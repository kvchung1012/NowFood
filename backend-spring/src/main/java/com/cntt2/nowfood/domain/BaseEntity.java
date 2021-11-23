package com.cntt2.nowfood.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 12:22 AM
 */
@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Type(
            type = "uuid-char"
    )
    @Column(
            name = "uuid",
            unique = true
    )
    private UUID uuid;
    @Column(
            name = "voided",
            columnDefinition = "boolean default false"
    )
    private Boolean voided = false;

    @CreatedBy
    @Column(name = "CreatedBy")
    private String createdBy;

    @CreatedDate
    @Column(name = "CreatedDate")
    private Date createdDate;

    @LastModifiedBy
    @Column(name = "UpdatedBy")
    private String updatedBy;

    @LastModifiedDate
    @Column(name = "UpdatedDate")
    private Date updatedDate;

    public BaseEntity() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
    }

}
