package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.dto.shop.ShopDto;
import com.cntt2.nowfood.dto.shop.ShopFormDto;
import com.cntt2.nowfood.exceptions.MessageEntity;
import com.cntt2.nowfood.mapper.ShopMapper;
import com.cntt2.nowfood.service.ShopService;
import com.cntt2.nowfood.utils.CommonUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/20/2021 4:31 PM
 */
@RestController
@RequestMapping("/api/shops")
@Api(tags = "Shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;
    private final ShopMapper shopMapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        ShopFormDto shop = shopService.findById(id);
        return new ResponseEntity<>(new MessageEntity(200, shop), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/products")
    public ResponseEntity<?> getProductsByShopId(@PathVariable Integer id) {
        ShopDto shop = shopService.findProductsById(id);
        return new ResponseEntity<>(new MessageEntity(200, shop), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getList() {
        List<ShopFormDto> shop = shopService.getAll()
                .stream().map(shopMapper::toFormDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new MessageEntity(200, shop), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody ShopFormDto form) {
        ShopFormDto shop = shopService.createShop(form);
        return new ResponseEntity<>(new MessageEntity(200, shop), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Shop shop = shopService.delete(id);
        return new ResponseEntity<>(new MessageEntity(200, !CommonUtils.isNull(shop)), HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ShopFormDto> create(@Valid @RequestBody ShopFormDto dto) {
        dto.setId(null);
        ShopFormDto result = shopService.createShop(dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
