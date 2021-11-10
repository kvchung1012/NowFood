package com.cntt2.nowfood.dto.shop;

import com.cntt2.nowfood.dto.BaseDto;
import com.cntt2.nowfood.dto.product.ProductDto;
import com.cntt2.nowfood.dto.size.SizeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/10/2021 2:59 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopDto extends BaseDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String shopName;
    private String shopImage;
    private String slug;
    private String shopPhone;
    private String shopEmail;
    private String shopAddress;
    private Double shopRate;
    private Date openTime;
    private Date closeTime;
    private Boolean isActive;
    private Boolean isMain;
    private List<ProductDto> products = new ArrayList<>();
    private List<SizeDto> sizes = new ArrayList<>();
}
