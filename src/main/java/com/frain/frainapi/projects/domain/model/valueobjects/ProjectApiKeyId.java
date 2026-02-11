package com.frain.frainapi.projects.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record ProjectApiKeyId(UUID value) {
    
    public ProjectApiKeyId {
        if (value == null) {
            throw new IllegalArgumentException("ProjectApiKeyId cannot be null");
        }
    }

    public static ProjectApiKeyId generate() {
        return new ProjectApiKeyId(UUID.randomUUID());
    }

    public static ProjectApiKeyId fromString(String id) {
        return new ProjectApiKeyId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
