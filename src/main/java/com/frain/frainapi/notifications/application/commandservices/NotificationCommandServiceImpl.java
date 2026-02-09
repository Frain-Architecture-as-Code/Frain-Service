package com.frain.frainapi.notifications.application.commandservices;

import com.frain.frainapi.notifications.domain.model.commands.SendNotificationCommand;
import com.frain.frainapi.notifications.domain.model.commands.UpdateNotificationStatusCommand;
import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationId;
import com.frain.frainapi.notifications.domain.services.NotificationCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationCommandServiceImpl implements NotificationCommandService {

    @Override
    public NotificationId handle(SendNotificationCommand command) {
        return null;
    }

    @Override
    public NotificationId handle(UpdateNotificationStatusCommand command) {
        return null;
    }
}
