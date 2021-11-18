package com.cntt2.nowfood.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 8:51 AM
 */
@Entity
@Table(name = "tbl_product_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "ProductId")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "CategoryId")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryByShopId")
    private CategoryByShop categoryByShop;

}
