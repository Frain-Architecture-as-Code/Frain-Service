package com.frain.frainapi.notifications.domain.model.queries;

import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationId;

public record GetNotificationByIdQuery(NotificationId notificationId) {
}
