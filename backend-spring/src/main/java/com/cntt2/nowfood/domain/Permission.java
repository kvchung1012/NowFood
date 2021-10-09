package com.cntt2.nowfood.domain;

import lombok.Getter;
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
public class Permission extends BaseEntity {

    private String permissionName;

    private String permissionKey;

}
