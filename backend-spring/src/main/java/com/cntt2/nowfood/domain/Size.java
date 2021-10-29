package com.cntt2.nowfood.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
 * @date 10/8/2021 8:50 AM
 */
@Entity
@Table(name = "tbl_size")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Size extends BaseEntity {

    @Column(name = "Name")
    private String name;

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
