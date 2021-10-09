package com.cntt2.nowfood.service;

import com.cntt2.nowfood.config.security.UserPrincipal;
import com.cntt2.nowfood.domain.User;

import java.util.Date;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 12:04 AM
 */
public interface UserService {
    User createUser(User user);
    UserPrincipal findByUsername(String username);
}
