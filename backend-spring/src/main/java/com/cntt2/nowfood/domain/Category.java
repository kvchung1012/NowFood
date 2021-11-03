package com.cntt2.nowfood.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 8:51 AM
 */
@Entity
@Table(name = "tbl_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity {

    @Column(name = "Code")
    private String code;

    @Column(name = "Name")
    private String name;

    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    private List<ProductCategory> productCategories = new ArrayList<>();
}
