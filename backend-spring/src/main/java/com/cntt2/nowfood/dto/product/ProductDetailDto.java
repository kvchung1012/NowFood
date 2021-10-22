package com.cntt2.nowfood.dto.product;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/22/2021 10:26 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductDetailDto extends ProductDto implements Serializable {
    private List<ProductSizeDto> productSizes = new ArrayList<>();
    private List<ProductDto> productOptions = new ArrayList<>();
    private List<ProductCategoryDto> productCategories = new ArrayList<>();
}
