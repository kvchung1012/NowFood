package com.cntt2.nowfood.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 8:50 AM
 */
@Entity(name = "ProductOption")
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductOption extends BaseEntity {

    @Column(name="MainProductId")
    private Integer mainProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubProductId")
    private Product subProduct;
}
