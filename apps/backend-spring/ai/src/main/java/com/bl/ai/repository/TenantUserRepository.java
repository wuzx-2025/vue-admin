package com.bl.ai.repository;

import com.bl.ai.domain.user.TenantUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantUserRepository extends JpaRepository<TenantUser, Long> {
    Page<TenantUser> findByTenantId(Long tenantId, Pageable pageable);
    List<TenantUser> findByTenantId(Long tenantId);
    java.util.Optional<TenantUser> findByTenantIdAndId(Long tenantId, Long id);
}
