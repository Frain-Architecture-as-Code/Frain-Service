package com.frain.frainapi.projects.domain.model.valueobjects;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ApiKey(@NotBlank(message = "Api key cannot be null or blank") String value) {
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
