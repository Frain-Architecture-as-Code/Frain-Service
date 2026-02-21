package com.frain.frainapi.notifications.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record ResourceId(UUID value) {
    public ResourceId {
        if (value == null) {
            throw new IllegalArgumentException("Resource Id cannot be null");
        }
    }
    public static ResourceId generate() {
        return new ResourceId(UUID.randomUUID());
    }

    public static ResourceId fromString(String value) {
        return new ResourceId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
