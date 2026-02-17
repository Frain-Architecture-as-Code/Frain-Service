package com.frain.frainapi.notifications.application.commandservices;

import com.frain.frainapi.notifications.domain.exceptions.NotificationNotFoundException;
import com.frain.frainapi.notifications.domain.model.Notification;
import com.frain.frainapi.notifications.domain.model.commands.SendNotificationCommand;
import com.frain.frainapi.notifications.domain.model.commands.UpdateNotificationStatusCommand;
import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationId;
import com.frain.frainapi.notifications.domain.services.NotificationCommandService;
import com.frain.frainapi.notifications.infrastructure.repositories.NotificationRepository;
import com.frain.frainapi.shared.domain.exceptions.InsufficientPermissionsException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationCommandServiceImpl
    implements NotificationCommandService
{

    private final NotificationRepository notificationRepository;

    public NotificationCommandServiceImpl(
        NotificationRepository notificationRepository
    ) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    @Transactional
    public NotificationId handle(SendNotificationCommand command) {
        var notificationId = NotificationId.generate();

        var notification = new Notification(
            notificationId,
            command.type(),
            command.message(),
            command.resourceId(),
            command.recipient(),
            command.sender()
        );

        notificationRepository.save(notification);

        return notificationId;
    }

    @Override
    @Transactional
    public NotificationId handle(UpdateNotificationStatusCommand command) {
        var result = notificationRepository.findById(command.notificationId());

        if (result.isEmpty()) {
            throw new NotificationNotFoundException(command.notificationId());
        }

        var notification = result.get();

        if (
            !notification.getRecipientEmail().equals(command.currentUserEmail())
        ) {
            throw new InsufficientPermissionsException(
                "User does not have permission to update this notification"
            );
        }

        switch (command.status()) {
            case READ -> notification.markAsRead();
            case UNREAD -> notification.markAsUnread();
            case ARCHIVED -> notification.markAsArchived();
        }

        notificationRepository.save(notification);

        return notification.getId();
    }
}
