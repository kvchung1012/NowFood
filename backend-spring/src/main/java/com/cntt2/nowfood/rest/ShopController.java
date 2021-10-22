package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.dto.shop.ShopFormDto;
import com.cntt2.nowfood.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/20/2021 4:31 PM
 */
@RestController
@RequestMapping("/api/shop")
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ShopFormDto> create(@Valid @RequestBody ShopFormDto dto) {
        ShopFormDto result = shopService.createShop(dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
