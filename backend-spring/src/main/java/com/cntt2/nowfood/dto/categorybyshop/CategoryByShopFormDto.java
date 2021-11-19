package com.cntt2.nowfood.dto.categorybyshop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/13/2021 8:25 AM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryByShopFormDto {
    private Integer id;
    private Integer shopId;
    private Boolean voided=false;
    @NotNull(message = "Tên danh mục không được để trống")
    @Size(max = 256, message = "Tên danh mục không được vượt quá 256 kí tự")
    private String name;

}
