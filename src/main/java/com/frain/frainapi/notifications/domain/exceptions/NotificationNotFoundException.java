package com.frain.frainapi.notifications.domain.exceptions;

import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationId;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(NotificationId notificationId) {
        super(String.format("Notification with id %s not found", notificationId.toString()));
    }
}
