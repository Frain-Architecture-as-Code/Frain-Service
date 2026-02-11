package com.frain.frainapi.notifications.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record NotificationId(UUID value) {
    public NotificationId {
        if (value == null) {
            throw new IllegalArgumentException("Notification Id cannot be null");
        }
    }
    public static NotificationId generate() {
        return new NotificationId(UUID.randomUUID());
    }

    public static  NotificationId fromString(String value) {
        return new NotificationId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
