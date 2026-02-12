package com.frain.frainapi.notifications.interfaces.rest.controllers;

import com.frain.frainapi.notifications.domain.exceptions.NotificationNotFoundException;
import com.frain.frainapi.notifications.domain.model.queries.GetAllUserNotificationsQuery;
import com.frain.frainapi.notifications.domain.model.queries.GetNotificationByIdQuery;
import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationId;
import com.frain.frainapi.notifications.domain.services.NotificationCommandService;
import com.frain.frainapi.notifications.domain.services.NotificationQueryService;
import com.frain.frainapi.notifications.interfaces.rest.controllers.assemblers.NotificationAssembler;
import com.frain.frainapi.notifications.interfaces.rest.controllers.assemblers.NotificationCommandAssembler;
import com.frain.frainapi.notifications.interfaces.rest.controllers.requests.UpdateNotificationStatusRequest;
import com.frain.frainapi.notifications.interfaces.rest.controllers.responses.NotificationResponse;
import com.frain.frainapi.shared.infrastructure.security.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@Slf4j
public class NotificationController {
    private final NotificationCommandService notificationCommandService;
    private final NotificationQueryService notificationQueryService;
    private final UserContext userContext;

    public NotificationController(NotificationCommandService notificationCommandService, NotificationQueryService notificationQueryService, UserContext userContext) {
        this.notificationCommandService = notificationCommandService;
        this.notificationQueryService = notificationQueryService;
        this.userContext = userContext;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAllUserNotifications() {
        var emailAddress = userContext.getCurrentUserEmail();

        var query = new GetAllUserNotificationsQuery(emailAddress);
        var notifications = notificationQueryService.handle(query);

        var response = NotificationAssembler.toResponsesFromEntities(notifications);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{notificationId}")
    public ResponseEntity<NotificationResponse> updateNotificationStatus(@RequestBody UpdateNotificationStatusRequest request, @PathVariable String notificationId) {

        var userEmail = userContext.getCurrentUserEmail();

        var command = NotificationCommandAssembler.toUpdateNotificationStatusCommandFromRequestAndString(request, notificationId, userEmail);

        var resultNotificationId = notificationCommandService.handle(command);

        var result = notificationQueryService.handle(new GetNotificationByIdQuery(resultNotificationId));

        if (result.isEmpty()) {
            throw new NotificationNotFoundException(resultNotificationId);
        }

        var notification = result.get();

        var response = NotificationAssembler.toResponseFromEntity(notification);

        return ResponseEntity.ok(response);
    }
}
