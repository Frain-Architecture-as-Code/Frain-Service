package com.frain.frainapi.projects.domain.model.queries;

import com.frain.frainapi.projects.domain.model.valueobjects.ApiKey;

/**
 * Query to find a ProjectApiKey by its API key value.
 */
public record GetProjectApiKeyByKeyQuery(ApiKey apiKey) {}
