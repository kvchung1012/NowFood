package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.config.security.JwtUtil;
import com.cntt2.nowfood.config.security.UserPrincipal;
import com.cntt2.nowfood.domain.Token;
import com.cntt2.nowfood.dto.user.UserRegisterDto;
import com.cntt2.nowfood.service.TokenService;
import com.cntt2.nowfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 11:58 PM
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;

    private final TokenService tokenService;

    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, TokenService tokenService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public UserRegisterDto register(@Valid @RequestBody UserRegisterDto userDto) {
        return userService.createUser(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @NotBlank @RequestParam String username,
                                   @Valid @NotBlank @RequestParam String password) {
        UserPrincipal userPrincipal = userService.findByUsername(username);
        if (!new BCryptPasswordEncoder().matches(password, userPrincipal.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tài khoản hoặc mật khẩu không chính xác !");
        }
        Token token = new Token();
        token.setToken(jwtUtil.generateToken(userPrincipal));
        token.setTokenExpDate(jwtUtil.generateExpirationDate());
        token.setCreatedBy(userPrincipal.getUsername());
        tokenService.createToken(token);
        return ResponseEntity.ok(token.getToken());
    }
}
