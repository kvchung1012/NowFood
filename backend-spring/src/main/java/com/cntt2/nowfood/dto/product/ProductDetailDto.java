package com.cntt2.nowfood.dto.product;

import com.cntt2.nowfood.dto.category.CategoryDto;
import com.cntt2.nowfood.dto.categorybyshop.CategoryByShopDto;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<CategoryDto> categories = new HashSet<>();
    private Set<CategoryByShopDto> categoryByShop = new HashSet<>();

}
