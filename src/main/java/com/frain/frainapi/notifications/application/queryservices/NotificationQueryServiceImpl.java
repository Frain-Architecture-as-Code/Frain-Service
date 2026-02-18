package com.frain.frainapi.notifications.application.queryservices;

import com.frain.frainapi.notifications.domain.model.Notification;
import com.frain.frainapi.notifications.domain.model.queries.GetAllUserNotificationsQuery;
import com.frain.frainapi.notifications.domain.model.queries.GetNotificationByIdQuery;
import com.frain.frainapi.notifications.domain.services.NotificationQueryService;
import com.frain.frainapi.notifications.infrastructure.repositories.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NotificationQueryServiceImpl implements NotificationQueryService {

    private final NotificationRepository notificationRepository;

    public NotificationQueryServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> handle(GetAllUserNotificationsQuery query) {
        return notificationRepository.findAllByRecipientEmail(query.userEmail());
    }

    @Override
    public Optional<Notification> handle(GetNotificationByIdQuery query) {
        return notificationRepository.findById(query.notificationId());
    }
}
