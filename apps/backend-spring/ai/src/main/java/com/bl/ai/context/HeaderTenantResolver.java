package com.bl.ai.context;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 * Simple resolver that reads X-Tenant-ID header and parses it as Long.
 */
@Component
public class HeaderTenantResolver implements TenantResolver {

    private static final String HEADER = "X-Tenant-ID";

    @Override
    public Long resolveTenantId(HttpServletRequest request) {
        String v = request.getHeader(HEADER);
        if (v == null || v.isBlank()) return null;
        try {
            return Long.parseLong(v.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
