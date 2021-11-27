package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.dto.order.OrderDto;
import com.cntt2.nowfood.exceptions.MessageEntity;
import com.cntt2.nowfood.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/27/2021 12:11 PM
 */
@RestController
@RequestMapping("/api/orders")
@Api(tags = "Orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @ApiOperation(value = "Api lấy chi tiết đơn hàng.")
    @GetMapping(value={"/{id}"})
    public ResponseEntity<?> getDetail(@PathVariable Integer id) {
        OrderDto order = orderService.findById(id);
        return ResponseEntity.ok().body(new MessageEntity(200, order));
    }
    @ApiOperation(value = "Api duyệt đơn hàng.")
    @GetMapping(value={"/approve/{id}"})
    public ResponseEntity<?> approveOrder(@PathVariable Integer id) {
        OrderDto order = orderService.approve(id);
        return ResponseEntity.ok().body(new MessageEntity(200, order));
    }
    @ApiOperation(value = "Api từ chối đơn hàng.")
    @GetMapping(value={"/reject/{id}"})
    public ResponseEntity<?> rejectOrder(@PathVariable Integer id) {
        OrderDto order = orderService.reject(id);
        return ResponseEntity.ok().body(new MessageEntity(200, order));
    }
}
