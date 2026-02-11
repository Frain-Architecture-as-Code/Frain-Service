package com.frain.frainapi.notifications.infrastructure.acl;

import com.frain.frainapi.notifications.domain.services.NotificationCommandService;
import com.frain.frainapi.notifications.interfaces.acl.NotificationContextAcl;
import com.frain.frainapi.notifications.interfaces.rest.controllers.assemblers.NotificationCommandAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationContextAclImpl implements NotificationContextAcl {

    private final NotificationCommandService notificationCommandService;

    public NotificationContextAclImpl(NotificationCommandService notificationCommandService) {
        this.notificationCommandService = notificationCommandService;
    }


    @Override
    public String sendNotification(String recipient, String sender, String message, String resourceId, String type) {
        var command = NotificationCommandAssembler.toSendNotificationCommand(recipient, sender, message, resourceId, type);

        var notificationId = notificationCommandService.handle(command);

        return notificationId.toString();
    }
}
