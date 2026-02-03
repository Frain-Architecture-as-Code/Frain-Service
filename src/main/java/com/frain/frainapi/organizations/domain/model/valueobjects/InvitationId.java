package com.frain.frainapi.organizations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record InvitationId(UUID value) {
    public InvitationId {
        if (value == null) {
            throw new IllegalArgumentException("Invitation Id cannot be null");
        }
    }

    public static InvitationId generate() {
        return new InvitationId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
