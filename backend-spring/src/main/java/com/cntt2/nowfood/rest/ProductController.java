package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.domain.Product;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/5/2021 11:49 PM
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(value = "/search")
    @PreAuthorize("hasAnyAuthority('USER_READ')")
    public ResponseEntity<Page<Product>> getByPage() {
        // todos: test api get product
        Page<Product> page = this.productService.getPage(new SearchDto(1,5));
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER_CREATE')")
    public ResponseEntity getAll() {
        // todos: test api get product
        List<Product> products = this.productService.getAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
