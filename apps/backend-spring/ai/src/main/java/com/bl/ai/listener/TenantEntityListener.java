package com.bl.ai.listener;

import com.bl.ai.context.TenantContext;
import com.bl.ai.domain.common.AbstractEntity;
import com.bl.ai.domain.common.TenantScopedEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

/**
 * JPA entity listener that fills audit timestamps and tenant id for tenant-scoped entities.
 */
public class TenantEntityListener {

    @PrePersist
    public void prePersist(Object entity) {
        // Set timestamps for any AbstractEntity
        if (entity instanceof AbstractEntity) {
            AbstractEntity e = (AbstractEntity) entity;
            e.setCreatedAt(LocalDateTime.now());
            e.setUpdatedAt(LocalDateTime.now());
        }

        // If it's tenant-scoped, ensure tenantId is populated from TenantContext when available
        if (entity instanceof TenantScopedEntity) {
            TenantScopedEntity te = (TenantScopedEntity) entity;
            Long tenantId = TenantContext.getTenantId();
            if (te.getTenantId() == null && tenantId != null) {
                te.setTenantId(tenantId);
            }
        }
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof AbstractEntity) {
            AbstractEntity e = (AbstractEntity) entity;
            e.setUpdatedAt(LocalDateTime.now());
        }
    }
}
