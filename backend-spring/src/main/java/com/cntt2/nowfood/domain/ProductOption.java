package com.cntt2.nowfood.domain;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "MainProductId")
    private Product mainProduct;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "SubProductId")
    @NotFound(action = NotFoundAction.IGNORE)
    private Product subProduct;
}
