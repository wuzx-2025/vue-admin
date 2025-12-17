package com.bl.ai.context;

import jakarta.servlet.http.HttpServletRequest;

public interface TenantResolver {
    /**
     * Resolve tenant id from the incoming request. Return null if not found.
     */
    Long resolveTenantId(HttpServletRequest request);
}
