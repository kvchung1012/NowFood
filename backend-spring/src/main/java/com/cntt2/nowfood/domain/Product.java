package com.cntt2.nowfood.domain;

import com.cntt2.nowfood.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Column(name = "Price")
    private Double price;

    @Column(name = "TotalOrder")
    private Integer totalOrder=0;

    @Column(name ="IsSoldOut")
    private Boolean isSoldOut=false;

    // Todos: Mapping to Restaurant
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="createdByShop")
    private Shop shop;

    // Product n - n Size => {Product 1 - n ProductSize n - 1 Size}
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @NotFound(action = NotFoundAction.IGNORE)
    private List<ProductSize> productSizes = new ArrayList<>();

    // Todos: Product 1 -n ProductOptions
    @OneToMany(
            mappedBy = "mainProduct",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<ProductOption> productOptions = new HashSet<>();

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<ProductCategory> productCategories = new HashSet<>();

    @Transient
    public String getImage() {
        if (this.image == null || this.getId() == null) return null;

        return "/product-photos/" + this.getId() + "/" + this.image;
    }
    @PrePersist
    @PreUpdate
    public void setSlug() {
        this.slug = CommonUtils.toSlug(this.name);
    }

    public void addSize(ProductSize size) {
        if (size != null) {
            if (productSizes == null) {
                productSizes = new ArrayList<>();
            }
            size.setProduct(this);
            productSizes.add(size);
        }
    }

    public void addOption(Product product) {
        if (product != null) {
            if (productOptions == null) {
                productOptions = new HashSet<>();
            }
            ProductOption option = new ProductOption(this, product);
            productOptions.add(option);
        }
    }

    public void addCategory(ProductCategory category) {
        if (category != null) {
            if (productCategories == null) {
                productCategories = new HashSet<>();
            }
            category.setProduct(this);
            productCategories.add(category);
        }
    }
}
