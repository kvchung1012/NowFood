package com.cntt2.nowfood.mapper;

import com.cntt2.nowfood.domain.Product;
import com.cntt2.nowfood.dto.product.ProductDetailDto;
import com.cntt2.nowfood.dto.product.ProductDto;
import com.cntt2.nowfood.dto.product.ProductFormDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/10/2021 3:16 PM
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product formToEntity(ProductFormDto form);

    Product formToEntity(ProductFormDto form, @MappingTarget Product update);

    ProductFormDto toFormDto(Product entity);
    ProductDto toDto(Product entity);
    ProductDetailDto toDetailDto(Product entity);
}
