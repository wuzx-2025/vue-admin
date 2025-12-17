package com.bl.ai.web.controller;

import com.bl.ai.domain.tenant.Tenant;
import com.bl.ai.service.TenantService;
import com.bl.ai.web.dto.TenantDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/platform/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    public ResponseEntity<TenantDto> create(@RequestBody TenantDto dto) {
        Tenant t = new Tenant();
    t.setCode(dto.getCode());
    t.setName(dto.getName());
    t.setStatus(dto.getStatus());
        Tenant saved = tenantService.create(t);
        dto.setId(saved.getId());
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<TenantDto>> list() {
        List<Tenant> list = tenantService.listAll();
        List<TenantDto> dtos = list.stream().map(t -> {
            TenantDto d = new TenantDto();
            d.setId(t.getId()); d.setCode(t.getCode()); d.setName(t.getName()); d.setStatus(t.getStatus());
            return d;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantDto> get(@PathVariable Long id) {
        Tenant t = tenantService.getById(id);
        TenantDto d = new TenantDto(); d.setId(t.getId()); d.setCode(t.getCode()); d.setName(t.getName()); d.setStatus(t.getStatus());
        return ResponseEntity.ok(d);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantDto> update(@PathVariable Long id, @RequestBody TenantDto dto) {
        Tenant t = new Tenant(); t.setCode(dto.getCode()); t.setName(dto.getName()); t.setStatus(dto.getStatus());
        Tenant updated = tenantService.update(id, t);
        dto.setId(updated.getId());
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tenantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
