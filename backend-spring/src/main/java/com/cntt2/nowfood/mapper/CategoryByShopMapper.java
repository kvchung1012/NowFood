package com.cntt2.nowfood.mapper;

import com.cntt2.nowfood.domain.CategoryByShop;
import com.cntt2.nowfood.dto.categorybyshop.CategoryByShopDto;
import com.cntt2.nowfood.dto.categorybyshop.CategoryByShopFormDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/13/2021 9:24 AM
 */
@Mapper(componentModel = "spring")
public interface CategoryByShopMapper {
    CategoryByShop toEntity(CategoryByShopFormDto form);

    CategoryByShop toEntity(CategoryByShopFormDto form, @MappingTarget CategoryByShop update);

    CategoryByShopDto toDto(CategoryByShop entity);

    CategoryByShopFormDto toFormDto(CategoryByShop entity);
}
