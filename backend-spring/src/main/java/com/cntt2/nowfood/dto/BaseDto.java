package com.cntt2.nowfood.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 4:06 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseDto extends AuditableDto {
    private Integer id;
    private Boolean voided;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
}
