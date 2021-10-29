package com.cntt2.nowfood.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 1:00 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFormDto implements Serializable {
    private Integer id;
    private String name;
    private String image;
    private String description;
    private Boolean isMain;
    private List<ProductSizeDto> sizes = new ArrayList<>();

    private List<Integer> options = new ArrayList<>();
    @Size(message = "Danh mục sản phẩm không được để trống", min = 1)
    private List<Integer> categories = new ArrayList<>();

    @Size(message = "Danh mục sản phẩm của cửa hàng không được để trống", min = 1)
    private List<Integer> shopCategories = new ArrayList<>();
}
