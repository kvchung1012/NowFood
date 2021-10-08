package com.cntt2.nowfood.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 8:51 AM
 */
@Entity
@Table(name = "tbl_ProductCategory")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCategory extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryId")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryByShopId")
    private CategoryByShop categoryByShop;
}
