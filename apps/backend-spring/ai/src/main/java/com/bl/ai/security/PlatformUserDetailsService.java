package com.bl.ai.security;

import com.bl.ai.domain.platform.PlatformUser;
import com.bl.ai.repository.PlatformUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PlatformUserDetailsService implements UserDetailsService {

    private final PlatformUserRepository repository;

    public PlatformUserDetailsService(PlatformUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PlatformUser user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new PlatformUserDetails(user);
    }
}
