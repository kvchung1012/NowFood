package com.cntt2.nowfood.dto.size;

import com.cntt2.nowfood.dto.BaseDto;
import com.cntt2.nowfood.dto.shop.ShopFormDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/10/2021 3:32 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SizeDto extends BaseDto implements Serializable {
    private String name;
    private ShopFormDto createdByShop;
}
