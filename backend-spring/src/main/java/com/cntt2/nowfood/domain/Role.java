package com.cntt2.nowfood.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 11:40 PM
 */
@Entity
@Table(name = "tbl_role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {

    private String roleName;

    private String roleKey;

    // Role n - n permissions
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinTable(name = "tbl_role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private Set<Permission> permissions = new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "role")
    private User user;
}
