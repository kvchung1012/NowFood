package com.cntt2.nowfood.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 9:44 AM
 */
@Entity
@Table(name = "tbl_token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token extends BaseEntity {

    @Column(name="Token",length = 1000)
    private String token;

    @Column(name="TokenExpDate")
    private Date tokenExpDate;

}
