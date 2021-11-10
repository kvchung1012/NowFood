package com.cntt2.nowfood.mapper;

import com.cntt2.nowfood.domain.SystemMasterData;
import com.cntt2.nowfood.dto.systemdata.SystemKeyDataFormDto;
import org.mapstruct.Mapper;

/**
 * @author Vanh
 * @version 1.0
 * @date 11/10/2021 6:09 PM
 */
@Mapper(componentModel = "spring")
public interface SystemKeyMapper {
    SystemMasterData toEntity(SystemKeyDataFormDto form);
    SystemKeyDataFormDto toDto(SystemMasterData form);
}
