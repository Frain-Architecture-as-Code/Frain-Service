package com.frain.frainapi.projects.interfaces.rest.controllers.responses;

/**
 * Response for listing API keys.
 * Note: The full apiKey value is NOT included for security.
 */
public record ProjectApiKeyResponse(
        String id,
        String projectId,
        String memberId,
        String apiKeyPrefix,
        String lastUsedAt,
        String createdAt
) {}
