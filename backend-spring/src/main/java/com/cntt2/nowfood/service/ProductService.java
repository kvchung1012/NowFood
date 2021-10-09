package com.cntt2.nowfood.service;

import com.cntt2.nowfood.domain.Product;
import com.cntt2.nowfood.dto.SearchDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 12:10 AM
 */
public interface ProductService {
    Product getOne(Integer id);
    Page<Product> getPage(SearchDto product);
}
