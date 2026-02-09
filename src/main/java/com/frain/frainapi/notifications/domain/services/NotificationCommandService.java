package com.frain.frainapi.notifications.domain.services;

import com.frain.frainapi.notifications.domain.model.commands.SendNotificationCommand;
import com.frain.frainapi.notifications.domain.model.commands.UpdateNotificationStatusCommand;
import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationId;

public interface NotificationCommandService {
    NotificationId handle(SendNotificationCommand command);
    NotificationId handle(UpdateNotificationStatusCommand command);
}
