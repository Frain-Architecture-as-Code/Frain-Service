package com.frain.frainapi.notifications.domain.services;

import com.frain.frainapi.notifications.domain.model.Notification;
import com.frain.frainapi.notifications.domain.model.queries.GetAllUserNotificationsQuery;
import com.frain.frainapi.notifications.domain.model.queries.GetNotificationByIdQuery;

import java.util.List;
import java.util.Optional;

public interface NotificationQueryService {
    List<Notification> handle(GetAllUserNotificationsQuery query);
    Optional<Notification> handle(GetNotificationByIdQuery query);
}
