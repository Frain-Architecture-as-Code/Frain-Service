package com.frain.frainapi.projects.interfaces.rest.controllers.responses;

/**
 * Response when an API key is created.
 * The apiKey value is only shown once at creation time.
 */
public record ProjectApiKeyCreatedResponse(
        String id,
        String projectId,
        String memberId,
        String apiKey,
        String createdAt
) {}
