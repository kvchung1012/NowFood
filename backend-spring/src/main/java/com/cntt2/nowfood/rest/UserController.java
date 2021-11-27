package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.config.security.UserPrincipal;
import com.cntt2.nowfood.dto.order.OrderDto;
import com.cntt2.nowfood.dto.order.OrderSearchDto;
import com.cntt2.nowfood.exceptions.MessageEntity;
import com.cntt2.nowfood.service.OrderService;
import com.cntt2.nowfood.service.ShopService;
import com.cntt2.nowfood.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/27/2021 12:03 PM
 */
@RestController
@RequestMapping("/api/users")
@Api(tags = "Users")
@RequiredArgsConstructor
public class UserController {
    private final ShopService shopService;
    private final OrderService orderService;


    @ApiOperation(value = "Tìm kiếm đơn hàng nâng cao cho users [Phân trang].")
    @PostMapping(value = "/orders")
    public ResponseEntity<?> getOrders(@RequestBody OrderSearchDto form) {
        UserPrincipal user = SecurityUtils.getCurrentUser();
        if(user != null)
            form.setCustomerId(user.getUserId());
        Page<OrderDto> orders = orderService.findByAdvSearch(form);
        return new ResponseEntity<>(new MessageEntity(200, orders), HttpStatus.OK);
    }
}
