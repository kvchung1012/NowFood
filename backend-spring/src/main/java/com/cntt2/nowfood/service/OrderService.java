package com.cntt2.nowfood.service;

import com.cntt2.nowfood.domain.Order;
import com.cntt2.nowfood.dto.order.CartDto;
import com.cntt2.nowfood.dto.order.FeeOrder;
import com.cntt2.nowfood.dto.order.OrderDto;
import com.cntt2.nowfood.dto.order.OrderSearchDto;
import org.springframework.data.domain.Page;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/25/2021 10:31 PM
 */
public interface OrderService extends GenericService<Order, Integer>{
    OrderDto checkout(CartDto form);
    FeeOrder getFeeOrder(Order order);
    Page<OrderDto> findByAdvSearch(OrderSearchDto form);
    OrderDto findById(Integer id);
    OrderDto approve(Integer id);
    OrderDto reject(Integer id);
}
