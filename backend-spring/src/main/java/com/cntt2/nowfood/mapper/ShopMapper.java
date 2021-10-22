package com.cntt2.nowfood.mapper;

import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.dto.shop.ShopFormDto;
import org.mapstruct.Mapper;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/20/2021 4:12 PM
 */
@Mapper(componentModel = "spring")
public interface ShopMapper {
    Shop formToEntity(ShopFormDto form);
    ShopFormDto toFormDto(Shop entity);
}
