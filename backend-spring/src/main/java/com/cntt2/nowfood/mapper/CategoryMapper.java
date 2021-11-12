package com.cntt2.nowfood.mapper;

import com.cntt2.nowfood.domain.Category;
import com.cntt2.nowfood.domain.Shop;
import com.cntt2.nowfood.dto.category.CategoryDto;
import com.cntt2.nowfood.dto.category.CategoryFormDto;
import com.cntt2.nowfood.dto.shop.ShopDto;
import com.cntt2.nowfood.dto.shop.ShopFormDto;
import org.mapstruct.Mapper;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/11/2021 12:02 AM
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category formToEntity(CategoryFormDto form);
    CategoryFormDto toFormDto(Category entity);
    CategoryDto toDto(Category entity);
}
