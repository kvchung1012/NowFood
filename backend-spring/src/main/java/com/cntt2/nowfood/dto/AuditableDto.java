package com.cntt2.nowfood.dto;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/9/2021 4:08 PM
 */
@Data
public class AuditableDto {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private UUID uuid;
    private Boolean voided;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
}
