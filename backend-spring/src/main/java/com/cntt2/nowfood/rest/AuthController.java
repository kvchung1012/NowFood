package com.cntt2.nowfood.rest;

import com.cntt2.nowfood.config.security.JwtUtil;
import com.cntt2.nowfood.config.security.UserPrincipal;
import com.cntt2.nowfood.domain.Token;
import com.cntt2.nowfood.dto.auth.JwtResponse;
import com.cntt2.nowfood.dto.auth.LoginRequest;
import com.cntt2.nowfood.dto.user.UserRegisterDto;
import com.cntt2.nowfood.exceptions.MessageEntity;
import com.cntt2.nowfood.service.TokenService;
import com.cntt2.nowfood.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 11:58 PM
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final TokenService tokenService;

    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto userDto) {
        String messValid = userService.validUser(userDto);
        if(!"".equals(messValid))
            return ResponseEntity.badRequest().body(new MessageEntity(400,messValid));
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest login) {
        UserPrincipal userPrincipal = userService.findByUsername(login.getUsername());
        if (!new BCryptPasswordEncoder().matches(login.getPassword(), userPrincipal.getPassword())) {
            return ResponseEntity.badRequest().body(new MessageEntity(400,"Tài khoản hoặc mật khẩu không chính xác !"));
        }else if(!userPrincipal.getEnabled()) {
            return ResponseEntity.badRequest().body(new MessageEntity(400,"Tài khoản chưa được kích hoạt !"));
        }else if(userPrincipal.getVoided()){
            return ResponseEntity.badRequest().body(new MessageEntity(400,"Tài khoản của bạn đã bị khóa !"));
        }
        Token token = new Token();
        token.setToken(jwtUtil.generateToken(userPrincipal));
        token.setTokenExpDate(jwtUtil.generateExpirationDate());
        token.setCreatedBy(userPrincipal.getUsername());
        tokenService.createToken(token);
        JwtResponse jwt = new JwtResponse(token.getToken(),userPrincipal.getUsername());
        return ResponseEntity.ok(jwt);
    }
    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return userService.confirmToken(token);
    }

    @GetMapping(path = "/refresh-confirm")
    public String refreshConfirm(@RequestParam("email") String email) {
        return userService.refreshConfirm(email);
    }
    @GetMapping(path = "/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam("token") String token) {
        //todos: refreshToken()
        return ResponseEntity.ok(token);
    }
}
