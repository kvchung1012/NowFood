package com.cntt2.nowfood.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 11:41 PM
 */
@Entity
@Table(name = "tbl_permission")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BaseEntity {

    private String permissionName;

    private String permissionKey;

}
