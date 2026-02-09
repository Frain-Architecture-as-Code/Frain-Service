package com.frain.frainapi.notifications.domain.model.commands;

import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationId;
import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationStatus;

public record UpdateNotificationStatusCommand(NotificationId notificationId, NotificationStatus status) {
}
