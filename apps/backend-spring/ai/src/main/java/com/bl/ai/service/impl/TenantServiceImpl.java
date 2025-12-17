package com.bl.ai.service.impl;

import com.bl.ai.domain.tenant.Tenant;
import com.bl.ai.repository.TenantRepository;
import com.bl.ai.service.TenantService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Tenant create(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant update(Long id, Tenant tenant) {
        Tenant exist = tenantRepository.findById(id).orElseThrow(() -> new RuntimeException("Tenant not found"));
        exist.setName(tenant.getName());
        exist.setCode(tenant.getCode());
        exist.setStatus(tenant.getStatus());
        return tenantRepository.save(exist);
    }

    @Override
    public Tenant getById(Long id) {
        return tenantRepository.findById(id).orElseThrow(() -> new RuntimeException("Tenant not found"));
    }

    @Override
    public List<Tenant> listAll() {
        return tenantRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        tenantRepository.deleteById(id);
    }
}
