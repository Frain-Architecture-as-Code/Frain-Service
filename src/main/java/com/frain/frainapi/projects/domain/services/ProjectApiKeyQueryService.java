package com.frain.frainapi.projects.domain.services;

import com.frain.frainapi.projects.domain.model.ProjectApiKey;
import com.frain.frainapi.projects.domain.model.queries.GetProjectApiKeyByKeyQuery;
import com.frain.frainapi.projects.domain.model.queries.GetProjectApiKeysQuery;

import java.util.List;
import java.util.Optional;

public interface ProjectApiKeyQueryService {
    
    /**
     * Find a ProjectApiKey by its API key value.
     * Used for API key authentication.
     */
    Optional<ProjectApiKey> handle(GetProjectApiKeyByKeyQuery query);
    
    /**
     * Get all API keys for a project.
     */
    List<ProjectApiKey> handle(GetProjectApiKeysQuery query);
}
