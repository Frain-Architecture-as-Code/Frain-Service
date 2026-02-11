package com.frain.frainapi.projects.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Embeddable
public record ApiKey(@NotBlank(message = "Api key cannot be null or blank") String value) {
    
    private static final String PREFIX = "frain_";

    public static ApiKey generate() {
        return new ApiKey(PREFIX + UUID.randomUUID().toString());
    }

    public boolean isValid() {
        return value != null && value.startsWith(PREFIX) && value.length() > PREFIX.length();
    }
}
