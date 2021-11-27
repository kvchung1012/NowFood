package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.dto.order.CartDto;
import com.cntt2.nowfood.dto.order.OrderDto;
import com.cntt2.nowfood.exceptions.MessageEntity;
import com.cntt2.nowfood.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/25/2021 10:33 PM
 */
@RestController
@RequestMapping("/api/carts")
@Api(tags = "Cart")
@RequiredArgsConstructor
public class CartController {

    private final OrderService orderService;

    @ApiOperation(value = "Api thanh toán.")
    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@Valid @RequestBody CartDto form) {
        OrderDto order = orderService.checkout(form);
        return ResponseEntity.ok().body(new MessageEntity(200, order));
    }
}
