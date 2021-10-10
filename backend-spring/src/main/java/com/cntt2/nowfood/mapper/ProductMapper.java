package com.cntt2.nowfood.mapper;

import com.cntt2.nowfood.domain.Product;
import com.cntt2.nowfood.dto.product.ProductDto;
import org.mapstruct.Mapper;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/10/2021 3:16 PM
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product productDtoToEntity(ProductDto dto);
}
