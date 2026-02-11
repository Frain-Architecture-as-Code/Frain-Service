package com.frain.frainapi.projects.interfaces.rest.controllers.requests;

import jakarta.validation.constraints.NotBlank;

/**
 * Request to create an API key for a specific member.
 */
public record CreateApiKeyRequest(
        @NotBlank(message = "Target member ID is required")
        String targetMemberId
) {}
