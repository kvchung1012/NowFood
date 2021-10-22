package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.domain.Product;
import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.product.ProductDto;
import com.cntt2.nowfood.dto.product.ProductFormDto;
import com.cntt2.nowfood.service.ProductService;
import com.cntt2.nowfood.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/5/2021 11:49 PM
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    private final ShopService shopService;

    @Autowired
    public ProductController(ProductService productService, ShopService shopService) {
        this.productService = productService;
        this.shopService = shopService;
    }

    @PostMapping(value = "/search")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Page<ProductDto>> getByPage(@RequestBody SearchDto searchDto) {
        Optional<Shop> shop = shopService.getOwner();
        // todos: test api get product
        Page<ProductDto> page = productService.findByAdvSearch(searchDto,shop.orElse(null));
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductFormDto> create(@Valid @RequestBody ProductFormDto form) {
        // todos: author
        ProductFormDto result = productService.create(form);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
