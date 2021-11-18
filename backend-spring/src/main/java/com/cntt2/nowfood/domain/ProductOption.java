package com.cntt2.nowfood.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 8:50 AM
 */
@Entity
@Table(name = "tbl_product_option")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOption extends BaseEntity {

    @Column(name="MainProductId")
    private Integer mainProductId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "SubProductId")
    private Product subProduct;
}
