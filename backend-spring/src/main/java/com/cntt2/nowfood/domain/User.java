package com.cntt2.nowfood.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 9:15 AM
 */
@Entity
@Table(name = "tbl_User")
public class User extends BaseEntity {

    @OneToOne(mappedBy = "owner")
    private Shop shop;
}
