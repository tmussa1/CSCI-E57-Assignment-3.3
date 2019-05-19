package com.thoughtmechanix.security.utils;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN = "Auth";
    public static final String USER_ID = "tmx-user-id";
    public static final String ORGANIZATION_ID = "tmx-organization-id";

    private static final ThreadLocal<String> correlationId = new ThreadLocal<>();
    private static final ThreadLocal<String> authToken = new ThreadLocal<>();
    private static final ThreadLocal<String> userId = new ThreadLocal<>();
    private static final ThreadLocal<String> organizationId = new ThreadLocal<>();

    public static String getCorrelationId() {
        return correlationId.get();
    }

    public static void setCorrelationId(String corrId) {
        correlationId.set(corrId);
    }

    public static String getAuthToken() {
        return authToken.get();
    }

    public static String getUserId() {
        return userId.get();
    }

    public static String getOrganizationId() {
        return organizationId.get();
    }

    public static void setAuthToken(String token) {
        authToken.set(token);
    }

    public static void setUserId(String id) {
        userId.set(id);
    }

    public static void setOrganizationId(String orgId) {
        organizationId.set(orgId);
    }
}
