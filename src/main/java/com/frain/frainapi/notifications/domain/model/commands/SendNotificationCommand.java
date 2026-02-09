package com.frain.frainapi.notifications.domain.model.commands;

import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationMessage;
import com.frain.frainapi.notifications.domain.model.valueobjects.ResourceId;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;

public record SendNotificationCommand(NotificationMessage message, ResourceId resourceId, EmailAddress recipient, EmailAddress sender) {
}
