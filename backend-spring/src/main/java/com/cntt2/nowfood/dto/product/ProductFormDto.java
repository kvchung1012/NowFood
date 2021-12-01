package com.cntt2.nowfood.dto.product;

import lombok.*;

import javax.validation.constraints.NotEmpty;
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
    private Integer shopId;
    @NotEmpty(message ="Tên sản phẩm không được để trống !")
    @Size(max = 256,message ="Tên sản phẩm không được quá 256 kí tự !")
    private String name;
    private String image;
    @Size(max = 1280,message ="Chi tiết sản phẩm không được quá 1280 kí tự !")
    private String description;
    private Boolean isMain;
    private Boolean voided=false;
    private Double price;
    private List<ProductSizeDto> sizes = new ArrayList<>();
    private List<Integer> options = new ArrayList<>();
    @Size(message = "Danh mục sản phẩm không được để trống", min = 1)
    private List<Integer> categories = new ArrayList<>();

    @Size(message = "Danh mục sản phẩm của cửa hàng không được để trống", min = 1)
    private List<Integer> shopCategories = new ArrayList<>();

    @Data
    public static class ProductSizeDto{
        private Double price;
        private Integer stockInDay;
        // Id size
        private Integer idSize;
    }
}
