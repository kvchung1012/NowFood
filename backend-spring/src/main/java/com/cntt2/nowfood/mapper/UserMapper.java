package com.cntt2.nowfood.mapper;

import com.cntt2.nowfood.domain.User;
import com.cntt2.nowfood.dto.user.UserRegisterDto;
import org.mapstruct.Mapper;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 10:32 PM
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    User userRegisterToUser(UserRegisterDto dto);
    UserRegisterDto toUserRegisterDto(User user);
    UserRegisterDto toUser(User user);
}
