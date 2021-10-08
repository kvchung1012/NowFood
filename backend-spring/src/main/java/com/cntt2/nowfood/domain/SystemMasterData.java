package com.cntt2.nowfood.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/8/2021 11:02 AM
 */
@Entity
@Table(name = "`SystemMasterData`")
@Data
@EqualsAndHashCode(callSuper = true)
public class SystemMasterData extends BaseEntity {

    @Column(name = "ParentId")
    private Integer parentId;

    @Column(name = "Code")
    private String code;

    @Column(name = "Value")
    private String value;

    @Column(name = "SubValue")
    private String subValue;

    // Thứ tự ưu tiên
    @Column(name = "SortOrder")
    private Integer sortOrder;
}
