package com.cntt2.nowfood.domain;

import com.cntt2.nowfood.utils.CommonUtils;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 8:58 AM
 */
@Entity
@Table(name = "tbl_shop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Shop extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Column(name = "ShopName")
    private String shopName;

    @Column(name = "ShopImage", length = 512)
    private String shopImage;

    @Column(name = "Slug")
    private String slug;

    @Column(name = "ShopPhone")
    private String shopPhone;

    @Column(name = "ShopEmail")
    private String shopEmail;

    @Column(name = "ShopAddress")
    private String shopAddress;

    @Column(name = "ShopRate")
    private Double shopRate;

    @Column(name = "OpenTime")
    private Date openTime;

    @Column(name = "CloseTime")
    private Date closeTime;

    @Column(name = "IsActive")
    private Boolean isActive;

    @Column(name = "IsMain")
    private Boolean isMain;

    // 1 user - 1 shop
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "OwnerId")
    @NotFound(action = NotFoundAction.IGNORE)
    private User owner;

    @OneToMany(
            mappedBy = "shop",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Product> products = new ArrayList<>();

    @OneToMany(
            mappedBy = "createdByShop",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Size> sizes = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void setSlug() {
        this.slug = CommonUtils.toSlug(this.shopName);
    }
}
