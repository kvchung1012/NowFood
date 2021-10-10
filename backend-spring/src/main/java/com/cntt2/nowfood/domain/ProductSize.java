package com.cntt2.nowfood.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 8:50 AM
 */
@Entity
@Table(name = "tbl_product_size")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSize extends BaseEntity {

    @Column(name="Price",precision = 10,scale = 2)
    private Double price;

    @Column(name="StockInDay",precision=10,scale=2)
    private Integer stockInDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SizeId")
    private Size size;
}
