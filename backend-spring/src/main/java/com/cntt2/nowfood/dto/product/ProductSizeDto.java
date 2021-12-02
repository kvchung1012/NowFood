package com.cntt2.nowfood.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/20/2021 6:25 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizeDto implements Serializable {
    private Double price;
    private SizeDto size;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SizeDto{
        private Integer id;
        private String name;
    }
}
