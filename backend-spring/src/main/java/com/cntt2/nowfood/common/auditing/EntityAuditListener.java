package com.cntt2.nowfood.common.auditing;

import javax.persistence.PostRemove;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 12:32 AM
 */
public class EntityAuditListener {
    public EntityAuditListener() {
    }

    @PostRemove
    public void postRemove(AuditableEntity auditableEntity) {
        // todos: Remove by ... at ...
    }

    @PrePersist
    public void beforePersit(AuditableEntity auditableEntity) {
        // todos: Create by ... at ...
    }

    @PreUpdate
    public void beforeMerge(AuditableEntity auditableEntity) {
    }
}
