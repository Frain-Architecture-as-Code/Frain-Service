package com.frain.frainapi.shared.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record UserId(UUID value) {
    public UserId {
        if (value == null) {
            throw new IllegalArgumentException("Member Id cannot be null");
        }
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public static UserId fromString(String id) {
        return new UserId(UUID.fromString(id));
    }
}
