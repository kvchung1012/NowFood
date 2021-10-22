package com.cntt2.nowfood.mapper;

import com.cntt2.nowfood.domain.ProductSize;
import com.cntt2.nowfood.dto.product.ProductSizeDto;
import org.mapstruct.Mapper;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/21/2021 12:23 AM
 */
@Mapper(componentModel = "spring")
public interface ProductSizeMapper {
    ProductSize toEntity(ProductSizeDto dto);
}
