package com.cntt2.nowfood.service;

import com.cntt2.nowfood.domain.Order;
import com.cntt2.nowfood.domain.Product;
import com.cntt2.nowfood.dto.cart.CartDto;
import com.cntt2.nowfood.dto.cart.FeeOrder;
import com.cntt2.nowfood.dto.cart.OrderDto;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/25/2021 10:31 PM
 */
public interface OrderService extends GenericService<Order, Integer>{
    OrderDto checkout(CartDto form);
    FeeOrder getFeeOrder(Order order);
}
