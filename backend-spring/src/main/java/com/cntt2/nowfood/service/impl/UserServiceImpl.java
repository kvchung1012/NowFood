package com.cntt2.nowfood.service.impl;

import com.cntt2.nowfood.common.Constants;
import com.cntt2.nowfood.config.security.UserPrincipal;
import com.cntt2.nowfood.domain.ConfirmationToken;
import com.cntt2.nowfood.domain.Role;
import com.cntt2.nowfood.domain.User;
import com.cntt2.nowfood.dto.user.UserRegisterDto;
import com.cntt2.nowfood.mapper.UserMapper;
import com.cntt2.nowfood.repository.RoleRepository;
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

import static com.cntt2.nowfood.common.Constants.ROLE_ADMIN;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 12:05 AM
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends GenericServiceImpl<User,Integer> implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
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
        // set member
        Role role = new Role();
        role = roleRepository.findByRoleKey(Constants.ROLE_CUSTOMER);
        user.setRole(role);
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
                        new IllegalStateException("Kh??ng t??m th???y token"));

        if (confirmationToken.getConfirmAt() != null) {
            throw new IllegalStateException("Email ???? ???????c x??c nh???n");
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
            return "Email x??c th???c kh??ng t???n t???i!";
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
            result = "T??n t??i kho???n ???? t???n t???i trong h??? th???ng";
        }
        else if(userRepository.existsByEmail(userDto.getEmail())){
            result = "Email ???? t???n t???i trong h??? th???ng";
        }
        else if(userRepository.existsByPhoneNumber(userDto.getPhoneNumber())){
            result = "S??? ??i???n tho???i ???? t???n t???i trong h??? th???ng";
        }
        return result;
    }

}
