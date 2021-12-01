package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.domain.*;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.product.*;
import com.cntt2.nowfood.exceptions.ValidException;
import com.cntt2.nowfood.mapper.ProductCategoryMapper;
import com.cntt2.nowfood.mapper.ProductMapper;
import com.cntt2.nowfood.repository.*;
import com.cntt2.nowfood.service.ProductService;
import com.cntt2.nowfood.service.ShopService;
import com.cntt2.nowfood.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 12:10 AM
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends GenericServiceImpl<Product, Integer> implements
        ProductService {
  private final ShopService shopService;

  private final ProductRepository productRepository;

  private final SizeRepository sizeRepository;

  private final CategoryRepository categoryRepository;

  private final CategoryByShopRepository categoryByShopRepository;

  private final ProductCategoryRepository productCategoryRepository;

  private final ProductMapper productMapper;

  private final ProductCategoryMapper productCategoryMapper;


  @Override
  public Product findById(Integer id) {
    return productRepository.findById(id).orElse(null);
  }

  @Override
  public ProductDetailDto findDetailById(Integer id) {
    Optional<Product> product = productRepository.findById(id);
    ProductDetailDto data = productMapper.toDetailDto(product.orElse(null));
    List<ProductCategory> categories = productCategoryRepository.findByProductId(data.getId());
    data.setProductOptions(
            product.get().getProductOptions().stream()
                    .map(x -> productMapper.toDto(x.getSubProduct()))
                    .collect(Collectors.toList())
    );
    data.setCategories(
            categories.stream()
                    .map(x -> productCategoryMapper.toCategoryDto(x.getCategory()))
                    .collect(Collectors.toSet())
    );
    data.setCategoryByShop(
            categories.stream()
                    .map(x -> productCategoryMapper.toCategoryByShopDto(x.getCategoryByShop()))
                    .collect(Collectors.toSet())
    );
    return data;
  }

  @Override
  public Page<ProductDto> findByShop(SearchDto dto, Shop shop) {
    Pageable pageable = PageRequest.of(dto.getPageIndex() - 1, dto.getPageSize());
    Integer shopId = null;
    if (null != shop) shopId = shop.getId();
    Page<Product> entities = productRepository.findByShop(shopId, pageable);
    return entities
            .map(productMapper::toDto);
  }

  @Override
  public List<ProductDto> findByShop(Integer id,Boolean isMain) {
    return
            productRepository
                    .findByShop(id,isMain)
                    .stream()
                    .map(productMapper::toDto)
                    .collect(Collectors.toList());
  }

  @Override
  public Page<ProductDto> findByAdvSearch(ProductSearchDto dto) {
    Pageable pageable = CommonUtils.getPageRequest(dto);
    Page<Product> entities = productRepository.findAdvSearch(dto, pageable);
    return entities.map(productMapper::toDto);
  }

  private String validProduct(ProductFormDto dto, Integer shopId) {
    if (null != dto.getSizes()) {
      List<Integer> idsSize = dto.getSizes().stream().map(ProductFormDto.ProductSizeDto::getIdSize)
              .collect(Collectors.toList());
      List<Size> sizes = sizeRepository.findByIds(idsSize, shopId);
      if (idsSize.size() != sizes.size()) {
        return "Kích thước món ăn không phù hợp với thông tin cửa hàng !";
      }
    }
    // valid options
    if (null != dto.getOptions()) {
      List<Product> products = productRepository.findOptionsByIds(dto.getOptions(), shopId);
      if (products.size() != dto.getOptions().size()) {
        return "Cấu hình món phụ không phù hợp thông tin cửa hàng !";
      }
    }
    // valid categories
    if (null != dto.getCategories()) {
      List<Category> categories = categoryRepository.findByIds(dto.getCategories());
      if (categories.size() != dto.getCategories().size()) {
        return "Danh mục sản phẩm không phù hợp thông tin cửa hàng !";
      }
    } else {
      return "Danh mục sản phẩm không được để trống ! ";
    }
    // valid Category shop
    if (null != dto.getShopCategories()) {
      List<CategoryByShop> categories = categoryByShopRepository
              .findByIds(dto.getShopCategories(), shopId);
      if (categories.size() != dto.getShopCategories().size()) {
        return "Danh mục sản phẩm của cửa hàng không tồn tại !";
      }
    } else {
      return "Danh mục sản phẩm của cửa hàng không được để trống !";
    }
    return "";
  }

  private Product valid(ProductFormDto form) {
    Optional<Shop> owner = shopService.getShopLogin(true);
    Boolean isAdmin = owner.isEmpty();
    // Nếu là admin thi thông tin cửa hàng được lấy từ form => check null
    if (isAdmin && form.getShopId() == null) throw new ValidException("Cửa hàng không được để trống !");
    Integer shopId = isAdmin ? form.getShopId() : owner.get().getId();
    Product entity = null;
    // update
    if (!CommonUtils.isNull(form.getId())) {
      entity = this.productRepository.findById(form.getId())
              .orElseThrow(() -> new ValidException("Sản phẩm không tồn tại"));
      if (!isAdmin && null != owner.get().getId()) {
        Boolean isOwner = owner.get().getId().equals(entity.getShop().getId());
        if (!isOwner)
          throw new ValidException("Sản phẩm không thuộc về cửa hàng !");
      }
    }
    // add
    else {
      entity = new Product();
      // Nếu là admin thì lấy ở form, không thì lấy ở thông tin login
      Shop shop = this.shopService.getById(shopId);
      if (null == shop) throw new ValidException("Cửa hàng không hợp lệ ");
      entity.setShop(shop);
    }
    // 2.2: valid sizes,options,categories
    String valid = validProduct(form, shopId);
    if (!"".equals(valid)) {
      throw new ValidException(valid);
    }
    return entity;
  }

  @Override
  @Transactional
  public ProductFormDto saveOrUpdate(ProductFormDto form) {
    if (form == null) return null;
    else {
      // Check role
      Product entity = valid(form);
      entity = productMapper.formToEntity(form, entity);
      // 2.3: add sizes,options,categories to Product
      entity = this.productRepository.save(entity);
      addProductSizes(form, entity);
      addOptions(form, entity);
      addCategories(form, entity);
      return productMapper.toFormDto(entity);
    }
  }

  private void addOptions(ProductFormDto form, Product entity) {
    if (null != form.getOptions()) {
      if (!entity.getProductOptions().isEmpty())
        entity.getProductOptions().clear();
      form.getOptions().forEach(o -> {
                Product product = this.productRepository.getById(o);
                entity.addOption(product);
              }
      );
    }
  }

  private void addCategories(ProductFormDto form, Product entity) {
    if (null != form.getCategories() && null != form.getShopCategories()) {
      if (!entity.getProductCategories().isEmpty())
        entity.getProductCategories().clear();
      form.getCategories().forEach(c -> {
                Category category = this.categoryRepository.getById(c);
                form.getShopCategories().forEach(sc -> {
                  CategoryByShop categoryByShop = this.categoryByShopRepository.getById(sc);
                  entity.addCategory(new ProductCategory(entity, category, categoryByShop));
                });
              }
      );
    }
  }

  private void addProductSizes(ProductFormDto form, Product entity) {
    if (null != form.getSizes()) {
      if (!entity.getProductSizes().isEmpty())
        entity.getProductSizes().clear();
      form.getSizes().forEach(s -> {
                Size size = this.sizeRepository.getById(s.getIdSize());
                entity.addSize(new ProductSize(s.getPrice(), s.getStockInDay(), entity, size));
              }
      );
    }
  }
}
