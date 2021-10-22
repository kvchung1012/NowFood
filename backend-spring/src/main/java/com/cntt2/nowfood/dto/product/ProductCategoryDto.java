package com.cntt2.nowfood.dto.product;

import com.cntt2.nowfood.domain.Category;
import com.cntt2.nowfood.domain.CategoryByShop;
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
    private Category category;
    private CategoryByShop categoryByShop;
}
