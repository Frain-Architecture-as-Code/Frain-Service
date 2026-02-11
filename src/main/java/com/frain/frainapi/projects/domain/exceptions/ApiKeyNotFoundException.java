package com.frain.frainapi.projects.domain.exceptions;

import com.frain.frainapi.projects.domain.model.valueobjects.ProjectApiKeyId;

public class ApiKeyNotFoundException extends RuntimeException {
    public ApiKeyNotFoundException(ProjectApiKeyId apiKeyId) {
        super("API key not found with id: " + apiKeyId.toString());
    }

    public ApiKeyNotFoundException(String message) {
        super(message);
    }
}
