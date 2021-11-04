package com.cntt2.nowfood.dto.user;

import com.cntt2.nowfood.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 4:06 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto extends BaseDto {
    @NotEmpty(message = "Họ và tên không được để trống")
    private String fullName;

    @NotEmpty(message = "username không được để trống")
    @Size(min = 8, message = "username phải từ 8 kí tự trở lên")
    private String username;

    @JsonProperty("password")
    @NotEmpty(message = "Password không được để trống")
    @Size(min = 8, message = "Password phải từ 8 kí tự trở lên")
    private String hashPassword;

    @Email(message = "Email không hợp lệ")
    private String email;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b")
    private String phoneNumber;

    private Date birthDate;

    private String address;

}
