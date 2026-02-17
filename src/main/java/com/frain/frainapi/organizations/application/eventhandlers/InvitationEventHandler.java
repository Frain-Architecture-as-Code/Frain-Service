package com.frain.frainapi.organizations.application.eventhandlers;

import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationType;
import com.frain.frainapi.notifications.interfaces.acl.NotificationContextAcl;
import com.frain.frainapi.organizations.domain.exceptions.MemberNotFoundException;
import com.frain.frainapi.organizations.domain.model.commands.EnrollMemberCommand;
import com.frain.frainapi.organizations.domain.model.events.InvitationAcceptedEvent;
import com.frain.frainapi.organizations.domain.model.events.InvitationSentEvent;
import com.frain.frainapi.organizations.domain.model.queries.GetMemberByIdQuery;
import com.frain.frainapi.organizations.domain.services.MemberCommandService;
import com.frain.frainapi.organizations.domain.services.MemberQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class InvitationEventHandler {

    private final NotificationContextAcl notificationContextAcl;
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    public InvitationEventHandler(
            NotificationContextAcl notificationContextAcl, MemberCommandService memberCommandService,
            MemberQueryService memberQueryService) {
        this.notificationContextAcl = notificationContextAcl;
        this.memberCommandService = memberCommandService;
        this.memberQueryService = memberQueryService;
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

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleInvitationAccepted(InvitationAcceptedEvent event) {
        log.info("Handling InvitationAcceptedEvent");

        var invitation = event.invitation();

        var command = new EnrollMemberCommand(event.performBy(), invitation.getOrganizationId(), event.performByName(), invitation.getRole());

        var result = memberCommandService.handle(command);

        var member = memberQueryService.handle(new GetMemberByIdQuery(result))
                .orElseThrow(() -> new MemberNotFoundException(result));

        log.info(String.format("New member with name %s has been enrolled to organization with ID %s as %s",
                member.getName(),
                member.getOrganizationId(),
                member.getRole().toString()
        ));


    }
}
