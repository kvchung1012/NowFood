package com.cntt2.nowfood.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

/**
 * @author Vanh
 * @version 1.0
 * @date 12/2/2021 1:17 AM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String fullName;
    private String username;
    private String phoneNumber;
    private Date birthDate;
    private String address;
    private String email;
    private Date loginDate;
    private String loginIp;
    private String roleName;

}
