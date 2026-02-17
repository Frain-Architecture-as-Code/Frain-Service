package com.frain.frainapi.projects.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record OrganizationId(UUID value) {
    public OrganizationId {
        if (value == null) {
            throw new IllegalArgumentException("Organization Id cannot be null");
        }
    }

    public static OrganizationId generate() {
        return new OrganizationId(UUID.randomUUID());
    }

    public static OrganizationId fromString(String value) {
        return new OrganizationId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
