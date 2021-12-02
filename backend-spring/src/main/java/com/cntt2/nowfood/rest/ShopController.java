package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.dto.SearchDto;
import com.cntt2.nowfood.dto.categorybyshop.CategoryByShopDto;
import com.cntt2.nowfood.dto.order.OrderDto;
import com.cntt2.nowfood.dto.order.OrderSearchDto;
import com.cntt2.nowfood.dto.shop.ShopDetailDto;
import com.cntt2.nowfood.dto.shop.ShopDto;
import com.cntt2.nowfood.dto.shop.ShopFormDto;
import com.cntt2.nowfood.dto.size.SizeDto;
import com.cntt2.nowfood.exceptions.MessageEntity;
import com.cntt2.nowfood.mapper.CategoryByShopMapper;
import com.cntt2.nowfood.mapper.ShopMapper;
import com.cntt2.nowfood.mapper.SizeMapper;
import com.cntt2.nowfood.service.CategoryByShopService;
import com.cntt2.nowfood.service.OrderService;
import com.cntt2.nowfood.service.ShopService;
import com.cntt2.nowfood.service.SizeService;
import com.cntt2.nowfood.utils.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
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
    private final CategoryByShopService categoryByShopService;
    private final ShopService shopService;
    private final OrderService orderService;
    private final SizeService sizeService;
    private final ShopMapper shopMapper;
    private final CategoryByShopMapper categoryByShopMapper;
    private final SizeMapper sizeMapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        ShopFormDto shop = shopService.findById(id);
        return new ResponseEntity<>(new MessageEntity(200, shop), HttpStatus.OK);
    }
    @ApiOperation(value = "Tìm kiếm nâng cao shop [Phân trang].")
    @PostMapping("/search-adv")
    public ResponseEntity<?> getListAdv(@Valid @RequestBody SearchDto dto) {
        Page<ShopDto> result = shopService.findByAdvSearch(dto);
        return new ResponseEntity<>(new MessageEntity(200,result), HttpStatus.OK);
    }
    @GetMapping(value = "/{id}/products")
    public ResponseEntity<?> getProductsByShopId(@PathVariable Integer id) {
        ShopDetailDto shop = shopService.findDetailByShopId(id);
        shop.setSizes(null);
        return new ResponseEntity<>(new MessageEntity(200, shop), HttpStatus.OK);
    }
    @ApiOperation(value = "Tìm kiếm đơn hàng nâng cao shop [Phân trang].")
    @PostMapping(value = "/orders")
    public ResponseEntity<?> getOrders(@RequestBody OrderSearchDto form) {
        Optional<Shop> shop = shopService.getShopLogin(true);
        if(shop.isPresent())
            form.setShopId(shop.get().getId());
        Page<OrderDto> orders = orderService.findByAdvSearch(form);
        return new ResponseEntity<>(new MessageEntity(200, orders), HttpStatus.OK);
    }
    @ApiOperation(value = "Lấy danh mục theo quán ăn .")
    @GetMapping(value="/category-shop")
    public ResponseEntity<?> getCategories() {
        Integer shopId = null;
        Optional<Shop> shop = shopService.getShopLogin(true);
        if(shop.isPresent())
            shopId = shop.get().getId();
        List<CategoryByShopDto> sizes = categoryByShopService.findByShopId(shopId)
                .stream().map(categoryByShopMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new MessageEntity(200,sizes), HttpStatus.OK);
    }
    @ApiOperation(value = "Lấy danh mục theo quán ăn .")
    @GetMapping(value="/{id}/category-shop")
    public ResponseEntity<?> getCategoriesByShop(@PathVariable Integer id) {
        List<CategoryByShopDto> sizes = categoryByShopService.findByShopId(id)
                .stream().map(categoryByShopMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new MessageEntity(200,sizes), HttpStatus.OK);
    }
    @PostMapping(value = "/category-shop/search-adv")
    public ResponseEntity<?> getCategoryByShopAdv(@Valid @RequestBody SearchDto dto) {
        Optional<Shop> shop = shopService.getShopLogin(true);
        if(shop.isPresent())
            dto.setShopId(shop.get().getId());
        Page<CategoryByShopDto> result = categoryByShopService.findByAdvSearch(dto);
        return new ResponseEntity<>(new MessageEntity(200, result), HttpStatus.OK);
    }
    @GetMapping(value="/sizes")
    public ResponseEntity<?> getSizes() {
        Integer shopId = null;
        Optional<Shop> shop = shopService.getShopLogin(true);
        if(shop.isPresent())
            shopId = shop.get().getId();
        List<SizeDto> sizes = sizeService.findByShopId(shopId)
                .stream().map(sizeMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new MessageEntity(200,sizes), HttpStatus.OK);
    }
    @ApiOperation(value = "Tìm kiếm nâng cao kích thước [Phân trang].")
    @PostMapping("/sizes/search-adv")
    public ResponseEntity<?> getSizesAdv(@Valid @RequestBody SearchDto dto) {
        Optional<Shop> shop = shopService.getShopLogin(true);
        if(shop.isPresent())
            dto.setShopId(shop.get().getId());
        Page<SizeDto> result = sizeService.findByAdvSearch(dto);
        return new ResponseEntity<>(new MessageEntity(200,result), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/sizes")
    public ResponseEntity<?> getSizesByShopId(@PathVariable Integer id) {
        ShopDetailDto shop = shopService.findDetailByShopId(id);
        shop.setProducts(null);
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
    @PreAuthorize("hasAnyAuthority('ADMIN','APPROVE_SHOP')")
    public ResponseEntity<ShopFormDto> create(@Valid @RequestBody ShopFormDto dto) {
        dto.setId(null);
        ShopFormDto result = shopService.createShop(dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
