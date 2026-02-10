package com.frain.frainapi.notifications.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.notifications.domain.model.commands.UpdateNotificationStatusCommand;
import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationId;
import com.frain.frainapi.notifications.interfaces.rest.controllers.requests.UpdateNotificationStatusRequest;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;

public class NotificationCommandAssembler {
    public static UpdateNotificationStatusCommand toUpdateNotificationStatusCommandFromRequest(UpdateNotificationStatusRequest request, NotificationId notificationId, EmailAddress userEmail) {
        return new UpdateNotificationStatusCommand(notificationId, request.newStatus(), userEmail);
    }
}
