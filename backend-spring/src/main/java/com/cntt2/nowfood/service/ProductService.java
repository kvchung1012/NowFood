package com.cntt2.nowfood.service;

import com.cntt2.nowfood.domain.Product;
import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.product.ProductDetailDto;
import com.cntt2.nowfood.dto.product.ProductDto;
import com.cntt2.nowfood.dto.product.ProductFormDto;
import com.cntt2.nowfood.dto.product.ProductSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 12:10 AM
 */
public interface ProductService extends GenericService<Product, Integer> {
    Product findById(Integer id);
    ProductDetailDto findDetailById(Integer id);
    Page<ProductDto> findByShop(SearchDto dto, Shop shop);
    List<ProductDto> findByShop(Integer id);
    Page<ProductDto> findByAdvSearch(ProductSearchDto dto);
    ProductFormDto saveOrUpdate(ProductFormDto form);
}
