package com.cntt2.nowfood.dto.product;

import com.cntt2.nowfood.domain.ProductCategory;
import com.cntt2.nowfood.domain.ProductOption;
import com.cntt2.nowfood.domain.ProductSize;
import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.dto.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/4/2021 8:47 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchDto extends SearchDto implements Serializable {
    private String name;
    private Boolean isMain;
    private Double rate;
    private Integer sizeId;
    private Set<Integer> categoryId = new HashSet<>();
    private Set<Integer> categoryShopId = new HashSet<>();
}
