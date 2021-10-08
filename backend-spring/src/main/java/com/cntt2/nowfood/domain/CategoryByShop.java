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
 * @date 10/8/2021 10:52 AM
 */
@Entity
@Table(name = "tbl_CategoryByShop")
@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryByShop extends BaseEntity{
    @Column(name = "Name")
    private String name;

    @Column(name = "CreatedByShop")
    private Integer createdByShop;

    @OneToMany(
            mappedBy = "categoryByShop",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProductCategory> productCategories = new ArrayList<>();
}
