package com.cntt2.nowfood.domain;

import com.cntt2.nowfood.config.security.UserPrincipal;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 9:15 AM
 */
@Entity
@Table(name = "tbl_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "FullName")
    private String fullName;

    @Column(name = "Username")
    @NotNull
    private String username;

    @Column(name = "HashPassword", length = 512)
    @NotNull
    private String hashPassword;

    @Column(name = "PhoneNumber", length = 50)
    private String phoneNumber;

    @Column(name = "BirthDate")
    private Date birthDate;

    @Column(name = "Address")
    private String address;

    @Column(name = "Email")
    private String email;

    @Column(name = "LoginDate")
    private Date loginDate;

    @Column(name = "ForgotCode", length = 50)
    private String forgotCode;

    @Column(name = "RefreshToken", length = 1000)
    private String refreshToken;

    @Column(name = "ExpriedToken")
    private Date expriedToken;

    @Column(name = "ExpriedForgot")
    private Date expriedForgot;

    @Column(name = "LoginIP")
    private String loginIp;

    @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY)
    @JsonBackReference
    private Shop shop;

    // user n-n roles
    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "RoleId", referencedColumnName = "id")
    private Role role;

    public User(UserPrincipal user) {
        this.setId(user.getUserId());
        this.username = user.getUsername();
        this.hashPassword = user.getPassword();
    }
}
