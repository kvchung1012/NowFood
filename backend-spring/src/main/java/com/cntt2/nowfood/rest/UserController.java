package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.config.security.UserPrincipal;
import com.cntt2.nowfood.domain.User;
import com.cntt2.nowfood.dto.order.OrderDto;
import com.cntt2.nowfood.dto.order.OrderSearchDto;
import com.cntt2.nowfood.dto.user.UserDto;
import com.cntt2.nowfood.exceptions.MessageEntity;
import com.cntt2.nowfood.mapper.UserMapper;
import com.cntt2.nowfood.repository.UserRepository;
import com.cntt2.nowfood.service.OrderService;
import com.cntt2.nowfood.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final OrderService orderService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @ApiOperation(value = "Tìm kiếm đơn hàng nâng cao cho users [Phân trang].")
    @PostMapping(value = "/orders")
    public ResponseEntity<?> getOrders(@RequestBody OrderSearchDto form) {
        UserPrincipal user = SecurityUtils.getCurrentUser();
        if(user != null)
            form.setCustomerId(user.getUserId());
        Page<OrderDto> orders = orderService.findByAdvSearch(form);
        return new ResponseEntity<>(new MessageEntity(200, orders), HttpStatus.OK);
    }
    @ApiOperation(value = "API lấy ra thông tin user đang Login.")
    @GetMapping()
    public ResponseEntity<?> getInfo() {
        UserPrincipal userLogin = SecurityUtils.getCurrentUser();
        User user = userRepository.findByUsername(userLogin.getUsername());
        UserDto result = userMapper.toUserDto(user);
        result.setRoleName(userLogin.getRole());
        return new ResponseEntity<>(new MessageEntity(200, result), HttpStatus.OK);
    }

}
