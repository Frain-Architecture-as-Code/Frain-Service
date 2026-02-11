package com.frain.frainapi.organizations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record MemberId(UUID value) {
    public MemberId {
        if (value == null) {
            throw new IllegalArgumentException("Member Id cannot be null");
        }
    }

    public static MemberId generate() {
        return new MemberId(UUID.randomUUID());
    }

    public static MemberId fromString(String id) {
        return new MemberId(UUID.fromString(id));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
