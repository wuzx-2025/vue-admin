package com.bl.ai.config;

import com.bl.ai.domain.platform.Permission;
import com.bl.ai.domain.platform.Role;
import com.bl.ai.domain.platform.PlatformUser;
import com.bl.ai.repository.PermissionRepository;
import com.bl.ai.repository.RoleRepository;
import com.bl.ai.repository.PlatformUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PlatformUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.initial.admin.username:admin}")
    private String initialAdminUsername;

    @Value("${app.initial.admin.password:admin123}")
    private String initialAdminPassword;

    public DataInitializer(RoleRepository roleRepository,
                           PermissionRepository permissionRepository,
                           PlatformUserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // create default permissions
        List<String> perms = List.of(
                "tenant:create","tenant:read","tenant:update","tenant:delete",
                "platform:user:create","platform:user:read","platform:user:update","platform:user:delete"
        );
        List<Permission> createdPerms = new ArrayList<>();
        for (String p : perms) {
            Optional<Permission> op = permissionRepository.findByCode(p);
            if (op.isPresent()) {
                createdPerms.add(op.get());
            } else {
                Permission perm = new Permission(); perm.setCode(p); perm.setDescription(p);
                createdPerms.add(permissionRepository.save(perm));
            }
        }

        // create roles
        Role adminRole = roleRepository.findByName("ROLE_PLATFORM_ADMIN").orElseGet(() -> {
            Role r = new Role(); r.setName("ROLE_PLATFORM_ADMIN"); r.setDescription("Platform administrator");
            r.setPermissions(new HashSet<>(createdPerms));
            return roleRepository.save(r);
        });

        roleRepository.findByName("ROLE_PLATFORM_OPERATOR").orElseGet(() -> {
            Role r = new Role(); r.setName("ROLE_PLATFORM_OPERATOR"); r.setDescription("Platform operator");
            // give read-only for platform users and tenants
            Set<Permission> readPerms = new HashSet<>();
            createdPerms.stream().filter(pr -> pr.getCode().endsWith(":read")).forEach(readPerms::add);
            r.setPermissions(readPerms);
            return roleRepository.save(r);
        });

        // create initial platform admin user if not exists
        Optional<PlatformUser> exist = userRepository.findByUsername(initialAdminUsername);
        if (exist.isEmpty()) {
            PlatformUser u = new PlatformUser();
            u.setUsername(initialAdminUsername);
            u.setPasswordHash(passwordEncoder.encode(initialAdminPassword));
            u.setDisplayName("Initial Admin");
            u.setEnabled(true);
            Set<Role> roles = new HashSet<>(); roles.add(adminRole);
            u.setRoles(roles);
            userRepository.save(u);
            log.info("Created initial platform admin user '{}'", initialAdminUsername);
        } else {
            log.info("Initial platform admin user '{}' already exists", initialAdminUsername);
        }
    }
}
