package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.config.security.UserPrincipal;
import com.cntt2.nowfood.domain.ConfirmationToken;
import com.cntt2.nowfood.domain.User;
import com.cntt2.nowfood.dto.user.UserRegisterDto;
import com.cntt2.nowfood.mapper.UserMapper;
import com.cntt2.nowfood.repository.UserRepository;
import com.cntt2.nowfood.service.ConfirmTokenService;
import com.cntt2.nowfood.service.EmailService;
import com.cntt2.nowfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 12:05 AM
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ConfirmTokenService confirmTokenService;

    @Value("${user.confirm.url}")
    private String url;

    @Override
    @Transactional
    public UserRegisterDto createUser(UserRegisterDto userDto) {
        userDto.setHashPassword(new BCryptPasswordEncoder().encode(userDto.getHashPassword()));
        User user = userMapper.userRegisterToUser(userDto);
        user.setUuid(UUID.randomUUID());
        user.setEnabled(false);
        user.setVoided(false);
        UserRegisterDto result = userMapper.toUserRegisterDto(userRepository.saveAndFlush(user));
        //todos: confirm email
        String confirmToken = confirmTokenService.generateToken(user);
        String link = url + confirmToken;
        emailService.confirmEmail(user.getEmail(),user.getFullName(),link);
        return result;
    }
    @Override
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Không tìm thấy token"));

        if (confirmationToken.getConfirmAt() != null) {
            throw new IllegalStateException("Email đã được xác nhận");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmTokenService.setConfirmedAt(token);
        userRepository.enableAppUser(
                confirmationToken.getUser().getEmail());
        return "confirmed";
    }

    @Override
    public String refreshConfirm(String email) {
        User user = userRepository.findByEmail(email);
        if(null == user)
            return "Email xác thực không tồn tại!";
        confirmTokenService.delete(user.getEmail());
        // new token
        String confirmToken = confirmTokenService.generateToken(user);
        String link = url + confirmToken;
        emailService.confirmEmail(user.getEmail(),user.getFullName(),link);
        return "";
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
            userPrincipal.setRole(user.getRole().getRoleKey());
            userPrincipal.setVoided(user.getVoided());
            userPrincipal.setEnabled(user.getEnabled());
            userPrincipal.setUserId(user.getId());
            userPrincipal.setUsername(user.getUsername());
            userPrincipal.setPassword(user.getHashPassword());
            userPrincipal.setAuthorities(authorities);
        }
        return userPrincipal;
    }

    @Override
    public String validUser(UserRegisterDto userDto) {
        String result = "";
        if(userRepository.existsByUsername(userDto.getUsername())) {
            result = "Tên tài khoản đã tồn tại trong hệ thống";
        }
        else if(userRepository.existsByEmail(userDto.getEmail())){
            result = "Email đã tồn tại trong hệ thống";
        }
        else if(userRepository.existsByPhoneNumber(userDto.getPhoneNumber())){
            result = "Số điện thoại đã tồn tại trong hệ thống";
        }
        return result;
    }

}
