package com.cntt2.nowfood.mapper;

import com.cntt2.nowfood.domain.Order;
import com.cntt2.nowfood.dto.cart.CartDto;
import com.cntt2.nowfood.dto.cart.OrderDto;
import org.mapstruct.Mapper;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/25/2021 10:58 AM
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(CartDto dto);
    OrderDto toDto(Order entity);
}
