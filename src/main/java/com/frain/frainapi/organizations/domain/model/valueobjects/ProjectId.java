package com.frain.frainapi.organizations.domain.model.valueobjects;

import java.util.UUID;

public record ProjectId(UUID value) {
    public ProjectId {
        if (value == null) {
            throw new IllegalArgumentException("Project Id cannot be null");
        }
    }

    public static ProjectId generate() {
        return new ProjectId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
