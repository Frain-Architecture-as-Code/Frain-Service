package com.frain.frainapi.organizations.domain.model.valueobjects;

import java.util.UUID;

public record OrganizationId(UUID value) {
    public OrganizationId {
        if (value == null) {
            throw new IllegalArgumentException("Organization Id cannot be null");
        }
    }

    public static OrganizationId generate() {
        return new OrganizationId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
