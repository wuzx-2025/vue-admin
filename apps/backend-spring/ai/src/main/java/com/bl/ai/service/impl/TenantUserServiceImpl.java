package com.bl.ai.service.impl;

import com.bl.ai.context.TenantContext;
import com.bl.ai.domain.user.TenantUser;
import com.bl.ai.repository.TenantUserRepository;
import com.bl.ai.service.TenantUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TenantUserServiceImpl implements TenantUserService {

    private final TenantUserRepository tenantUserRepository;

    public TenantUserServiceImpl(TenantUserRepository tenantUserRepository) {
        this.tenantUserRepository = tenantUserRepository;
    }

    @Override
    public TenantUser create(TenantUser user) {
        // TenantEntityListener will fill tenantId from TenantContext if available
        return tenantUserRepository.save(user);
    }

    @Override
    public TenantUser update(Long id, TenantUser user) {
        Long tenantId = TenantContext.getTenantId();
        TenantUser exist = tenantUserRepository.findByTenantIdAndId(tenantId, id).orElseThrow(() -> new RuntimeException("Not found or not allowed"));
        exist.setUsername(user.getUsername());
        exist.setEmail(user.getEmail());
        // password handling omitted (hashing)
        return tenantUserRepository.save(exist);
    }

    @Override
    public TenantUser getById(Long id) {
        Long tenantId = TenantContext.getTenantId();
        return tenantUserRepository.findByTenantIdAndId(tenantId, id).orElseThrow(() -> new RuntimeException("Not found or not allowed"));
    }

    @Override
    public Page<TenantUser> listForCurrentTenant(Pageable pageable) {
        Long tenantId = TenantContext.getTenantId();
        return tenantUserRepository.findByTenantId(tenantId, pageable);
    }

    @Override
    public void delete(Long id) {
        Long tenantId = TenantContext.getTenantId();
        TenantUser exist = tenantUserRepository.findByTenantIdAndId(tenantId, id).orElseThrow(() -> new RuntimeException("Not found or not allowed"));
        tenantUserRepository.delete(exist);
    }
}
