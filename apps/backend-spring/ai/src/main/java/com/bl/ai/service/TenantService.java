package com.bl.ai.service;

import com.bl.ai.domain.tenant.Tenant;

import java.util.List;

public interface TenantService {
    Tenant create(Tenant tenant);
    Tenant update(Long id, Tenant tenant);
    Tenant getById(Long id);
    List<Tenant> listAll();
    void delete(Long id);
}
