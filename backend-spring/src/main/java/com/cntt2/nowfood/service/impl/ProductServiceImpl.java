package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.domain.*;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.product.ProductDto;
import com.cntt2.nowfood.dto.product.ProductFormDto;
import com.cntt2.nowfood.dto.product.ProductSearchDto;
import com.cntt2.nowfood.dto.product.ProductSizeDto;
import com.cntt2.nowfood.exceptions.ValidException;
import com.cntt2.nowfood.mapper.ProductMapper;
import com.cntt2.nowfood.repository.CategoryByShopRepository;
import com.cntt2.nowfood.repository.CategoryRepository;
import com.cntt2.nowfood.repository.ProductRepository;
import com.cntt2.nowfood.repository.SizeRepository;
import com.cntt2.nowfood.service.ProductService;
import com.cntt2.nowfood.service.ShopService;
import com.cntt2.nowfood.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
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

  private final ProductRepository productRepository;

  private final ProductMapper productMapper;

  private final SizeRepository sizeRepository;

  private final CategoryRepository categoryRepository;

  private final CategoryByShopRepository categoryByShopRepository;

  private final ShopService shopService;

  @Override
  public Product findById(Integer id) {
    return productRepository.findById(id).orElse(null);
  }

  @Override
  public Page<ProductDto> findByShop(SearchDto dto, Shop shop) {
    Pageable pageable = PageRequest.of(dto.getPageIndex() - 1, dto.getPageSize());
    Integer shopId = null;
    if(null != shop) shopId = shop.getId();
    Page<Product> entities = productRepository.findByShop(shopId, pageable);
    return entities
            .map(productMapper::toDto);
  }

  @Override
  public Page<ProductDto> findByAdvSearch(ProductSearchDto dto) {
    Pageable pageable = CommonUtils.getPageRequest(dto);
    Page<Product> entities = productRepository.findAdvSearch(dto,pageable);
    return entities.map(productMapper::toDto);
  }

  private String validProduct(ProductFormDto dto, Integer shopId) {
    if (null != dto.getSizes()) {
      List<Integer> idsSize = dto.getSizes().stream().map(ProductSizeDto::getIdSize)
              .collect(Collectors.toList());
      List<Size> sizes = sizeRepository.findByIds(idsSize, shopId);
      if (idsSize.size() != sizes.size()) {
        return "Kích thước món ăn không phù hợp !";
      }
    }
    // valid options
    if (null != dto.getOptions()) {
      List<Product> products = productRepository.findOptionsByIds(dto.getOptions(), shopId);
      if (products.size() != dto.getOptions().size()) {
        return "Cấu hình món phụ không phù hợp !";
      }
    }
    // valid categories
    if (null != dto.getCategories()) {
      List<Category> categories = categoryRepository.findByIds(dto.getCategories());
      if (categories.size() != dto.getCategories().size()) {
        return "Danh mục sản phẩm không phù hợp !";
      }
    } else {
      return "Danh mục sản không được để trống !";
    }
    // valid Category shop
    if (null != dto.getShopCategories()) {
      List<CategoryByShop> categories = categoryByShopRepository
              .findByIds(dto.getShopCategories(), shopId);
      if (categories.size() != dto.getShopCategories().size()) {
        return "Danh mục sản phẩm của cửa hàng không tồn tại !";
      }
    } else {
      return "Danh mục sản của cửa hàng không được để trống !";
    }
    return "";
  }

  @Transactional
  @Override
  public ProductFormDto create(ProductFormDto dto) {
    // 1. Mapping to Entity
    Product entity = this.productMapper.formToEntity(dto);
    // 2. Save
    // 2.1 get Shop by User login
    Optional<Shop> owner = shopService.getOwnerLogin();
    Shop shop = owner.orElse(null);
    entity.setShop(shop);
    // 2.2: valid sizes,options,categories
    String valid = validProduct(dto, shop.getId());
    if (!"".equals(valid)) {
      throw new ValidException(valid);
    }
    // 2.3: add sizes,options,categories to Product
    if (null != dto.getSizes()) {
      List<ProductSize> sizes = new ArrayList<>();
      dto.getSizes().forEach(s -> {
                Size size = new Size();
                size.setId(s.getIdSize());
                sizes.add(new ProductSize(s.getPrice(), s.getStockInDay(), entity, size));
              }
      );
      entity.setProductSizes(sizes);
      // save trước lấy id nhét zô combo
      this.productRepository.save(entity);
    }
    if (null != dto.getOptions()) {
      Set<ProductOption> options = new HashSet<>();
      dto.getOptions().forEach(o -> {
                Product product = new Product();
                product.setId(o);
                options.add(new ProductOption(entity.getId(), product));
              }
      );
      entity.setProductOptions(options);
    }
    if (null != dto.getCategories() && null != dto.getShopCategories()) {
      Set<ProductCategory> categories = new HashSet<>();
      dto.getCategories().forEach(c -> {
                Category category = new Category();
                category.setId(c);
                dto.getShopCategories().forEach(sc -> {
                  CategoryByShop categoryByShop = new CategoryByShop();
                  categoryByShop.setId(sc);
                  categories.add(new ProductCategory(entity, category, categoryByShop));
                });
              }
      );
      entity.setProductCategories(categories);
    }
    Product product = this.productRepository.save(entity);
    // 3. Return dto
    return productMapper.toFormDto(product);
  }
}
