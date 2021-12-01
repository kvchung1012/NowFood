package com.cntt2.nowfood.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/25/2021 12:38 AM
 */

@Entity
@Table(name = "tbl_order_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="OrderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="ProductId")
    private Product product;

    @Column(name = "ProductName")
    private String productName;

    @Column(name = "Quantity")
    private Integer quantity=1;

    @Column(name = "Price")
    private Double price;

    @Column(name = "Note")
    private String note;
}
