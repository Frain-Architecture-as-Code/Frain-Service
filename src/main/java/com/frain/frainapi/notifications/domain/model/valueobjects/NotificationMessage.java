package com.frain.frainapi.notifications.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public record NotificationMessage(@NotBlank  String value) {

    public static  NotificationMessage fromString(String message) {
        return new NotificationMessage(message);
    }

    @Override
    public String toString() {
        return value;
    }
}
