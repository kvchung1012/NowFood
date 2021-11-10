package com.cntt2.nowfood.mapper;

import com.cntt2.nowfood.domain.Size;
import com.cntt2.nowfood.dto.size.SizeDto;
import com.cntt2.nowfood.dto.size.SizeFormDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/10/2021 4:41 PM
 */
@Mapper(componentModel = "spring")
public interface SizeMapper {
    Size toEntity(SizeFormDto form);
    Size toEntity(SizeFormDto form, @MappingTarget Size update);
    SizeDto toDto(Size entity);
    SizeFormDto toFormDto(Size entity);
}
