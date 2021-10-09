package com.cntt2.nowfood.domain;

import com.cntt2.nowfood.config.security.UserPrincipal;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 9:15 AM
 */
@Entity
@Table(name = "tbl_user")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "FullName")
    private String fullName;

    @Column(name = "Username")
    @NotNull
    private String username;

    @Column(name = "HashPassword",length=512)
    @NotNull
    private String hashPassword;

    @Column(name = "PhoneNumber",length=50)
    private String phoneNumber;

    @Column(name = "BirthDate")
    private Date birthDate;

    @Column(name = "Address")
    private String address;

    @Column(name = "LoginDate")
    private Date loginDate;

    @Column(name = "ForgotCode",length=50)
    private String forgotCode;

    @Column(name = "ExpriedForgot")
    private Date expriedForgot;

    @OneToOne(mappedBy = "owner")
    private Shop shop;

    // user n-n roles
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "RoleId", referencedColumnName = "id")
    private Role role;

    public User(UserPrincipal user) {
        this.setId(user.getUserId());
        this.username = user.getUsername();
        this.hashPassword = user.getPassword();
    }
}
