package com.frain.frainapi.projects.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
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
