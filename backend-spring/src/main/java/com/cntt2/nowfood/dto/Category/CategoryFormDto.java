package com.cntt2.nowfood.dto.Category;

import lombok.*;
import javax.validation.constraints.NotNull;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/3/2021 10:30 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryFormDto {
    private Integer id;
    private String code;
    @NotNull(message = "Tên danh mục sản phẩm không được để trống !")
    private String name;
}
