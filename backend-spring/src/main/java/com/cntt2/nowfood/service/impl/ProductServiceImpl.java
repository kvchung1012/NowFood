package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.domain.Product;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.repository.ProductRepository;
import com.cntt2.nowfood.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 12:10 AM
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getOne(UUID uuid) {
        return productRepository.findById(uuid).get();
    }

    @Override
    public Page<Product> getPage(SearchDto dto) {
        if (dto == null) {
            return null;
        }
        int pageIndex = dto.getPageIndex();
        int pageSize = dto.getPageSize();
        pageIndex = pageIndex > 0 ? --pageIndex : 0;
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Product> entities = null;
        if(dto.getKeyword()!= null && StringUtils.hasText(dto.getKeyword())){
            entities = productRepository.findByNameContaining(dto.getKeyword(), pageable);
        }else{
            entities = productRepository.findAll(pageable);
        }
        return entities;
    }
}
