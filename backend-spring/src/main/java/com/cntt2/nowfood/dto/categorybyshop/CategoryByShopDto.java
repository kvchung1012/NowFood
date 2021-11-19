package com.cntt2.nowfood.dto.categorybyshop;

import com.cntt2.nowfood.domain.ProductCategory;
import com.cntt2.nowfood.dto.BaseDto;
import com.cntt2.nowfood.dto.shop.ShopDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/13/2021 8:22 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryByShopDto extends BaseDto implements Serializable {
    private String name;
    private Integer createdByShop;
}
