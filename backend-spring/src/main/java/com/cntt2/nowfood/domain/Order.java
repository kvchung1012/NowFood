package com.cntt2.nowfood.domain;

import com.cntt2.nowfood.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/25/2021 12:35 AM
 */
@Entity
@Table(name = "tbl_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {

    @Column(name = "ShareCode")
    private String shareCode;

    @Column(name = "ShipName")
    private String shipName;

    @Column(name = "ShipAddress")
    private String shipAddress;

    @Column(name = "ShipMobile")
    private String shipMobile;

    @Column(name = "ShipPrice")
    private Double shipPrice=0.0;

    @Column(name = "Note")
    private String note;

    @Column(name = "Fee")
    private Double fee=0.0;

    @Column(name = "TotalPrice")
    private Double totalPrice=0.0;

    @Column(name = "ConfirmedAt")
    private DateTime confirmedAt;

    @Column(name = "OrderStatus")
    @Enumerated(EnumType.STRING)
    private Constants.OrderStatus orderStatus;

    @Column(name = "ConfirmedBy")
    private String confirmedBy;

    @Column(name = "OrderType")
    @Enumerated(EnumType.STRING)
    private Constants.OrderType orderType;

    @Column(name = "PaymentMethod")
    @Enumerated(EnumType.STRING)
    private Constants.PaymentMethod paymentMethod;

    @Column(name = "PaymentStatus")
    @Enumerated(EnumType.STRING)
    private Constants.PaymentStatus paymentStatus;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerId")
    @NotFound(action = NotFoundAction.IGNORE)
    private User customer;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ShopId")
    @NotFound(action = NotFoundAction.IGNORE)
    private Shop shop;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<OrderDetail> orderDetails;

    public void addOrderDetail(Product product,Integer quantity){
        if(orderDetails == null) orderDetails = new HashSet<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(this);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(quantity);
        orderDetail.setPrice(product.getPrice());
        orderDetails.add(orderDetail);
    }
    public Double getTotalPrice() {
        Double total = 0.0;
        if(orderDetails != null){
            total = orderDetails.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
        }
        total += this.fee + this.shipPrice;
        return total;
    }
}
