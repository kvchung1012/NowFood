package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.config.security.UserPrincipal;
import com.cntt2.nowfood.domain.User;
import com.cntt2.nowfood.dto.user.UserRegisterDto;
import com.cntt2.nowfood.mapper.UserMapper;
import com.cntt2.nowfood.repository.ProductRepository;
import com.cntt2.nowfood.repository.UserRepository;
import com.cntt2.nowfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 12:05 AM
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserRegisterDto createUser(UserRegisterDto userDto) {
        userDto.setHashPassword(new BCryptPasswordEncoder().encode(userDto.getHashPassword()));
        User user = userMapper.userRegisterToUser(userDto);
        UserRegisterDto result = userMapper.toUserRegisterDto(userRepository.saveAndFlush(user));
        return result;
    }

    @Override
    public UserPrincipal findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal();
        if (null != user) {
            Set<String> authorities = new HashSet<>();
            // Check permissions
            if (null != user.getRole()) {
                // Add role : ADMIN,USER...
                authorities.add(user.getRole().getRoleKey());
                // Add Permission : USER_READ,USER_CREATE...
                user.getRole().getPermissions().forEach(p -> authorities.add(p.getPermissionKey()));
            }
            userPrincipal.setUserId(user.getId());
            userPrincipal.setUsername(user.getUsername());
            userPrincipal.setPassword(user.getHashPassword());
            userPrincipal.setAuthorities(authorities);
        }
        return userPrincipal;
    }

}
