package com.cntt2.nowfood.service;

import com.cntt2.nowfood.domain.Product;
import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.product.ProductDto;
import com.cntt2.nowfood.dto.product.ProductFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 12:10 AM
 */
public interface ProductService extends GenericService<Product, Integer> {
    Product findById(Integer id);

    Page<ProductDto> findByAdvSearch(SearchDto dto, Shop shop);

    Page<ProductFormDto> findByShop(Integer id, Pageable pageable);

    ProductFormDto create(ProductFormDto dto);
}
