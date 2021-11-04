package com.cntt2.nowfood.service;

import com.cntt2.nowfood.config.security.UserPrincipal;
import com.cntt2.nowfood.domain.User;
import com.cntt2.nowfood.dto.user.UserRegisterDto;

import java.util.Date;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 12:04 AM
 */
public interface UserService {
    UserRegisterDto createUser(UserRegisterDto userDto);
    UserPrincipal findByUsername(String username);
    String validUser(UserRegisterDto userDto);
    String confirmToken(String token);
    String refreshConfirm(String email);
}
