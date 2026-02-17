package com.frain.frainapi.organizations.application.eventhandlers;

import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationType;
import com.frain.frainapi.notifications.interfaces.acl.NotificationContextAcl;
import com.frain.frainapi.organizations.domain.model.events.InvitationSentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class InvitationEventHandler {

    private final NotificationContextAcl notificationContextAcl;

    public InvitationEventHandler(
        NotificationContextAcl notificationContextAcl
    ) {
        this.notificationContextAcl = notificationContextAcl;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleInvitationAccepted(InvitationSentEvent event) {
        log.info("Handling InvitationSentEvent");

        notificationContextAcl.sendNotification(
            event.recipientEmail().toString(),
            event.senderEmail().toString(),
            String.format(
                "You have been invited to '%s'",
                event.organizationName().toString()
            ),
            event.invitationId().toString(),
                NotificationType.INVITATION.toString()
        );
    }
}
