package com.bl.ai.service;

import com.bl.ai.domain.user.TenantUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantUserService {
    TenantUser create(TenantUser user);
    TenantUser update(Long id, TenantUser user);
    TenantUser getById(Long id);
    Page<TenantUser> listForCurrentTenant(Pageable pageable);
    void delete(Long id);
}
