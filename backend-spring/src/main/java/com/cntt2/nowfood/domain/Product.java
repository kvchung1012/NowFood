package com.cntt2.nowfood.domain;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 12:08 AM
 */
@Entity
@Table(name = "tbl_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity{
    private static final long serialVersionUID = 1L;
    @Column(name = "Name")
    private String name;

    @Column(name = "Image")
    private String image;

    @Column(name = "Slug")
    private String slug;

    @Column(name = "Description")
    private String description;

    @Column(name = "IsMain")
    private Boolean isMain;

    @Column(name = "Rate")
    private Double rate;

    // Todos: Mapping to Restaurant
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="createdByShop")
    private Shop shop;

    // Product n - n Size => {Product 1 - n ProductSize n - 1 Size}
    @OneToMany(
            mappedBy = "product",
            orphanRemoval = true
    )
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProductSize> productSizes = new ArrayList<>();

    // Todos: Product 1 -n ProductOptions
    @OneToMany(
            mappedBy = "subProduct",
            orphanRemoval = true
    )
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<ProductOption> productOptions = new HashSet<>();

    @OneToMany(
            mappedBy = "product",
            orphanRemoval = true
    )
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<ProductCategory> productCategories = new HashSet<>();

}
