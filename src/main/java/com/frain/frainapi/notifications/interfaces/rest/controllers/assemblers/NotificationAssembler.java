package com.frain.frainapi.notifications.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.notifications.domain.model.Notification;
import com.frain.frainapi.notifications.interfaces.rest.controllers.responses.NotificationResponse;

import java.util.List;

public class NotificationAssembler {
    public static NotificationResponse toResponseFromEntity(Notification notification) {
        return new NotificationResponse(
                notification.getId().toString(),
                notification.getType().name(),
                notification.getMessage().toString(),
                notification.getSenderEmail().toString(),
                notification.getStatus().name(),
                notification.getResourceId().toString(),
                notification.getRecipientEmail().toString(),
                notification.getCreatedAt().toString(),
                notification.getUpdatedAt().toString()
        );
    }

    public static List<NotificationResponse> toResponsesFromEntities(List<Notification> notifications) {
        return notifications.stream()
                .map(NotificationAssembler::toResponseFromEntity)
                .toList();
    }
}
