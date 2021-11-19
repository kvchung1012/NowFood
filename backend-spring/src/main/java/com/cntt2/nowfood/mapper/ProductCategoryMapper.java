package com.cntt2.nowfood.mapper;

import com.cntt2.nowfood.domain.Category;
import com.cntt2.nowfood.domain.CategoryByShop;
import com.cntt2.nowfood.dto.category.CategoryDto;
import com.cntt2.nowfood.dto.categorybyshop.CategoryByShopDto;
import org.mapstruct.Mapper;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/17/2021 11:37 PM
 */
@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {
    CategoryDto toCategoryDto(Category entity);

    CategoryByShopDto toCategoryByShopDto(CategoryByShop entity);

}
