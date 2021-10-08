package com.cntt2.nowfood.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 8:50 AM
 */
@Entity(name = "Size")
@Table
@Data
@EqualsAndHashCode(callSuper = true)
public class Size extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="createdByShop")
    private Shop createdByShop;

    @OneToMany(
            mappedBy = "size",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProductSize> productSizes = new ArrayList<>();
}
