package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.domain.CategoryByShop;
import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.categorybyshop.CategoryByShopDto;
import com.cntt2.nowfood.dto.categorybyshop.CategoryByShopFormDto;
import com.cntt2.nowfood.dto.shop.ShopFormDto;
import com.cntt2.nowfood.dto.size.SizeDto;
import com.cntt2.nowfood.exceptions.MessageEntity;
import com.cntt2.nowfood.mapper.CategoryByShopMapper;
import com.cntt2.nowfood.service.CategoryByShopService;
import com.cntt2.nowfood.service.ShopService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/11/2021 12:59 AM
 */
@RestController
@RequestMapping("/api/shop-category")
@Api(tags = "Danh mục theo shop")
@RequiredArgsConstructor
public class CategoryByShopController {

    private final CategoryByShopService categoryByShopService;
    private final CategoryByShopMapper categoryByShopMapper;
    private final ShopService shopService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        CategoryByShopDto category = categoryByShopService.findById(id);
        return new ResponseEntity<>(new MessageEntity(200, category), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getList() {
        List<CategoryByShopDto> categories = categoryByShopService.findAll();
        return new ResponseEntity<>(new MessageEntity(200, categories), HttpStatus.OK);
    }
    @GetMapping(value="/shop")
    public ResponseEntity<?> getListByShop() {
        Integer shopId = null;
        Optional<Shop> shop = shopService.getOwnerLogin(false);
        if(shop.isPresent())
            shopId = shop.get().getId();
        List<CategoryByShopDto> sizes = categoryByShopService.findByShopId(shopId)
                .stream().map(categoryByShopMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new MessageEntity(200,sizes), HttpStatus.OK);
    }

    @PostMapping(value = "/search-adv")
    public ResponseEntity<?> getListAdv(@Valid @RequestBody SearchDto dto) {
        Page<CategoryByShopDto> result = categoryByShopService.findByAdvSearch(dto);
        return new ResponseEntity<>(new MessageEntity(200, result), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CategoryByShopFormDto form) {
        form.setId(null);

        CategoryByShop category = categoryByShopService.saveOrUpdate(form);
        return new ResponseEntity<>(new MessageEntity(200, category), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody CategoryByShopFormDto form) {
        CategoryByShop category = categoryByShopService.saveOrUpdate(form);
        return new ResponseEntity<>(new MessageEntity(200, category), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        CategoryByShop category = categoryByShopService.deleteById(id);
        return new ResponseEntity<>(new MessageEntity(200, category), HttpStatus.OK);
    }
}
