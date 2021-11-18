package com.cntt2.nowfood.dto.product;

import com.cntt2.nowfood.dto.category.CategoryDto;
import com.cntt2.nowfood.dto.categorybyshop.CategoryByShopDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/22/2021 10:31 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDto implements Serializable {
    private CategoryDto category;
    private CategoryByShopDto categoryByShop;
}
