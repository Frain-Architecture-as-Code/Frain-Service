package com.frain.frainapi.notifications.domain.model.commands;

import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationId;
import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationStatus;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;

public record UpdateNotificationStatusCommand(NotificationId notificationId, NotificationStatus status, EmailAddress currentUserEmail) {
}
