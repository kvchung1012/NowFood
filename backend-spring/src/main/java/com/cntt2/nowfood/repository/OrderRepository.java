package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/24/2021 10:31 PM
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
}
