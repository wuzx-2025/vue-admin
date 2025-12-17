package com.bl.ai.domain.tenant;

import com.bl.ai.listener.TenantEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;

import com.bl.ai.domain.common.AbstractEntity;

@Entity
@Table(name = "tenant")
@EntityListeners(TenantEntityListener.class)
public class Tenant extends AbstractEntity {

    @Column(name = "code", nullable = false, unique = true, length = 64)
    private String code;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "status", length = 32)
    private String status;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
