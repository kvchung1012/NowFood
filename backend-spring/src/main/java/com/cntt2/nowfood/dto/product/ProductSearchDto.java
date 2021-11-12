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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private Integer categoryId;
    private Integer categoryShopId;
}
