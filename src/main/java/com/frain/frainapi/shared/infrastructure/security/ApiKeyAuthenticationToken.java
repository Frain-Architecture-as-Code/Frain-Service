package com.frain.frainapi.shared.infrastructure.security;

import com.frain.frainapi.projects.domain.model.valueobjects.MemberId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectApiKeyId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * Authentication token for API key-based authentication.
 */
public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    private final String apiKey;
    private final ProjectId projectId;
    private final MemberId memberId;
    private final ProjectApiKeyId apiKeyId;

    /**
     * Creates an unauthenticated token (before validation).
     */
    public ApiKeyAuthenticationToken(String apiKey) {
        super(Collections.emptyList());
        this.apiKey = apiKey;
        this.projectId = null;
        this.memberId = null;
        this.apiKeyId = null;
        setAuthenticated(false);
    }

    /**
     * Creates an authenticated token (after successful validation).
     */
    public ApiKeyAuthenticationToken(
            String apiKey,
            ProjectId projectId,
            MemberId memberId,
            ProjectApiKeyId apiKeyId,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
        this.apiKey = apiKey;
        this.projectId = projectId;
        this.memberId = memberId;
        this.apiKeyId = apiKeyId;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return apiKey;
    }

    @Override
    public Object getPrincipal() {
        return apiKeyId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public MemberId getMemberId() {
        return memberId;
    }

    public ProjectApiKeyId getApiKeyId() {
        return apiKeyId;
    }
}
