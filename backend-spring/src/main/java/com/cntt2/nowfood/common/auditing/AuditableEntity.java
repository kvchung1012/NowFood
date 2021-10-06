package com.cntt2.nowfood.common.auditing;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 12:33 AM
 */
@MappedSuperclass
@EntityListeners({EntityAuditListener.class})
public class AuditableEntity implements Serializable {
    @Transient
    private static final long serialVersionUID = 4559994432567537044L;
    @Column(
            name = "create_date",
            nullable = false
    )
    @Type(
            type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime"
    )
    private LocalDateTime createDate;
    @Column(
            name = "created_by",
            length = 100,
            nullable = false
    )
    private String createdBy;
    @Column(
            name = "modify_date",
            nullable = true
    )
    @Type(
            type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime"
    )
    private LocalDateTime modifyDate;
    @Column(
            name = "modified_by",
            length = 100,
            nullable = true
    )
    private String modifiedBy;

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getModifyDate() {
        return this.modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public AuditableEntity() {
    }

    public AuditableEntity(AuditableEntity entity) {
        if (entity != null) {
            this.modifiedBy = entity.modifiedBy;
            this.modifyDate = entity.modifyDate;
            this.createdBy = entity.createdBy;
            this.createDate = entity.createDate;
        }

    }
}
