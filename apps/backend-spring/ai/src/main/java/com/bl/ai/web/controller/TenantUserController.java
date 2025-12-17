package com.bl.ai.web.controller;

import com.bl.ai.domain.user.TenantUser;
import com.bl.ai.service.TenantUserService;
import com.bl.ai.web.dto.TenantUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tenant/users")
public class TenantUserController {

    private final TenantUserService userService;

    public TenantUserController(TenantUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<TenantUserDto> create(@RequestBody TenantUserDto dto) {
        TenantUser u = new TenantUser();
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmail());
        TenantUser saved = userService.create(u);
        dto.setId(saved.getId());
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        Page<TenantUser> p = userService.listForCurrentTenant(PageRequest.of(page, size));
        var dtos = p.stream().map(u -> {
            TenantUserDto d = new TenantUserDto(); d.setId(u.getId()); d.setUsername(u.getUsername()); d.setEmail(u.getEmail()); return d;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantUserDto> get(@PathVariable Long id) {
        TenantUser u = userService.getById(id);
        TenantUserDto d = new TenantUserDto(); d.setId(u.getId()); d.setUsername(u.getUsername()); d.setEmail(u.getEmail());
        return ResponseEntity.ok(d);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantUserDto> update(@PathVariable Long id, @RequestBody TenantUserDto dto) {
        TenantUser u = new TenantUser(); u.setUsername(dto.getUsername()); u.setEmail(dto.getEmail());
        TenantUser updated = userService.update(id, u);
        dto.setId(updated.getId());
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
