package com.cntt2.nowfood.repository;

import com.cntt2.nowfood.domain.Order;
import com.cntt2.nowfood.dto.order.OrderSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/24/2021 10:31 PM
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    @Query(value = "select o from Order o " +
            "left join o.shop s " +
            "left join o.customer c " +
            "where (1=1) " +
            "and (o.shop.id = :#{#dto.getShopId()} or :#{#dto.getShopId()} is null) " +
            "and (c.id = :#{#dto.getCustomerId()} or :#{#dto.getCustomerId()} is null) " +
            "and (o.id = :#{#dto.getId()} or :#{#dto.getId()} is null) " +
            "and (o.uuid = :#{#dto.getUuid()} or :#{#dto.getUuid()} is null) " +
            "and ( " +
            "o.shipName LIKE %:#{#dto.getKeyword()}% " +
            "or o.shipAddress LIKE %:#{#dto.getKeyword()}% " +
            "or o.shipMobile LIKE %:#{#dto.getKeyword()}% " +
            "or s.shopName LIKE %:#{#dto.getKeyword()}% " +
            "or s.shopAddress LIKE %:#{#dto.getKeyword()}% " +
            "or s.shopPhone LIKE %:#{#dto.getKeyword()}% " +
            "or :#{#dto.getKeyword()} is null or :#{#dto.getKeyword()} = '' " +
            ") " +
            "and (o.orderStatus = :#{#dto.getOrderStatus()} or :#{#dto.getOrderStatus()} is null ) " +
            "and (o.orderType = :#{#dto.getOrderType()} or :#{#dto.getOrderType()} is null) " +
            "and (o.paymentMethod = :#{#dto.getPaymentMethod()} or :#{#dto.getPaymentMethod()} is null ) "+
            "and (o.paymentStatus = :#{#dto.getPaymentStatus()} or :#{#dto.getPaymentStatus()} is null) " +
            "and (o.voided = :#{#dto.getVoided()} or :#{#dto.getVoided()} is null) " +
            "and ( (o.createdDate BETWEEN :#{#dto.getFromDate()} AND :#{#dto.getToDate()})  or :#{#dto.getFromDate()} is null) "
    )
    Page<Order> findByAdvSearch(OrderSearchDto dto, Pageable pageable);
}
