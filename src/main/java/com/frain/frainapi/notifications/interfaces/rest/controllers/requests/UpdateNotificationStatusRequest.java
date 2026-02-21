package com.frain.frainapi.notifications.interfaces.rest.controllers.requests;

import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationStatus;

public record UpdateNotificationStatusRequest(NotificationStatus newStatus) {
}
