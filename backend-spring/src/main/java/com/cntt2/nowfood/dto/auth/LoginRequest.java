package com.cntt2.nowfood.dto.auth;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Size;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/28/2021 9:15 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull
    @Size(min = 6, message="Tên tài khoản có ít nhất 6 kí tự")
    private String username;
    @NotNull
    @Size(min = 6, message="Mật khẩu có ít nhất 6 kí tự")
    private String password;
    private String role;
}
