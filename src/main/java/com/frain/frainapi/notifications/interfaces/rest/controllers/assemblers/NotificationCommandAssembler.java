package com.frain.frainapi.notifications.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.notifications.domain.model.commands.SendNotificationCommand;
import com.frain.frainapi.notifications.domain.model.commands.UpdateNotificationStatusCommand;
import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationId;
import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationMessage;
import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationType;
import com.frain.frainapi.notifications.domain.model.valueobjects.ResourceId;
import com.frain.frainapi.notifications.interfaces.rest.controllers.requests.UpdateNotificationStatusRequest;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;

public class NotificationCommandAssembler {
    public static UpdateNotificationStatusCommand toUpdateNotificationStatusCommandFromRequest(UpdateNotificationStatusRequest request, NotificationId notificationId, EmailAddress userEmail) {
        return new UpdateNotificationStatusCommand(notificationId, request.newStatus(), userEmail);
    }
    public static SendNotificationCommand toSendNotificationCommand(String recipient, String sender, String message, String resourceId, String type) {
        return new SendNotificationCommand(new NotificationMessage(message), ResourceId.fromString(resourceId), new EmailAddress(recipient), new EmailAddress(sender), NotificationType.valueOf(type));
    }
}
