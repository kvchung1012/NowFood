package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.config.security.UserPrincipal;
import com.cntt2.nowfood.domain.User;
import com.cntt2.nowfood.repository.UserRepository;
import com.cntt2.nowfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public UserPrincipal findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal();
        if (null != user) {
            Set<String> authorities = new HashSet<>();
            // Check permissions
            if (null != user.getRole()) {
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
