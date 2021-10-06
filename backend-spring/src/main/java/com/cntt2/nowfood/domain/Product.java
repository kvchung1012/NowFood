package com.cntt2.nowfood.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 12:08 AM
 */
@Entity
@Table(name = "tbl_product")
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseObject{
    private static final long serialVersionUID = 1L;
    @Column(name = "name")
    private String name;
}
