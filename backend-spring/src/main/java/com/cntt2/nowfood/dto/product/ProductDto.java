package com.cntt2.nowfood.dto.product;

import com.cntt2.nowfood.dto.BaseDto;
import com.cntt2.nowfood.dto.shop.ShopFormDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/21/2021 9:30 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends BaseDto implements Serializable {
    private String name;
    private Double price;
    private Integer totalOrder;
    private String image;
    private String slug;
    private String description;
    private Boolean isMain;
    private Double rate;
    private ShopFormDto shop;
}
