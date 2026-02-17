package com.frain.frainapi.organizations.application.commandservices;

import com.frain.frainapi.organizations.domain.exceptions.InvitationNotFoundException;
import com.frain.frainapi.organizations.domain.exceptions.MemberNotFoundException;
import com.frain.frainapi.organizations.domain.exceptions.OrganizationNotFoundException;
import com.frain.frainapi.organizations.domain.model.Invitation;
import com.frain.frainapi.organizations.domain.model.commands.AcceptInvitationCommand;
import com.frain.frainapi.organizations.domain.model.commands.DeclineInvitationCommand;
import com.frain.frainapi.organizations.domain.model.commands.SendInvitationCommand;
import com.frain.frainapi.organizations.domain.model.events.InvitationAcceptedEvent;
import com.frain.frainapi.organizations.domain.model.events.InvitationDeclinedEvent;
import com.frain.frainapi.organizations.domain.model.events.InvitationSentEvent;
import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;
import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationStatus;
import com.frain.frainapi.organizations.domain.services.InvitationCommandService;
import com.frain.frainapi.organizations.infrastructure.repositories.InvitationRepository;
import com.frain.frainapi.organizations.infrastructure.repositories.MemberRepository;
import com.frain.frainapi.organizations.infrastructure.repositories.OrganizationRepository;
import com.frain.frainapi.shared.domain.exceptions.InsufficientPermissionsException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InvitationCommandServiceImpl implements InvitationCommandService {

    private final OrganizationRepository organizationRepository;
    private final MemberRepository memberRepository;
    private final InvitationRepository invitationRepository;

    private final ApplicationEventPublisher eventPublisher;

    public InvitationCommandServiceImpl(
        OrganizationRepository organizationRepository,
        MemberRepository memberRepository,
        InvitationRepository invitationRepository,
        ApplicationEventPublisher eventPublisher
    ) {
        this.organizationRepository = organizationRepository;
        this.memberRepository = memberRepository;
        this.invitationRepository = invitationRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public InvitationId handle(SendInvitationCommand command) {
        var targetOrganization = organizationRepository
            .findById(command.organizationId())
            .orElseThrow(() ->
                new OrganizationNotFoundException(command.organizationId().toString())
            );

        command.performBy().canInvitePeople();

        log.info(String.format("User %s is sending an invitation to %s for organization '%s'",
            command.performBy().getName(),
            command.targetEmail(),
            targetOrganization.getName()
        ));

        var invitation = new Invitation(
            InvitationId.generate(),
            command.organizationId(),
            command.performBy().getId(),
            command.targetEmail(),
            command.role()
        );

        invitationRepository.save(invitation);

        eventPublisher.publishEvent(
            new InvitationSentEvent(
                invitation.getId(),
                command.senderEmail(),
                invitation.getTargetEmail(),
                targetOrganization.getName()
            )
        );

        log.info(
            "Invitation with ID {} has been sent to {}.",
            invitation.getInviterId(),
            command.targetEmail()
        );

        return invitation.getId();
    }

    @Override
    @Transactional
    public InvitationId handle(DeclineInvitationCommand command) {
        var invitation = invitationRepository
            .findById(command.invitationId())
            .orElseThrow(() ->
                new InvitationNotFoundException(command.invitationId())
            );

        if (!invitation.getTargetEmail().equals(command.currentUserEmail())) {
            throw new InsufficientPermissionsException(
                "You do not have permission to decline this invitation."
            );
        }

        invitation.updateStatus(InvitationStatus.DECLINED);

        invitationRepository.save(invitation);

        eventPublisher.publishEvent(
            new InvitationDeclinedEvent(
                invitation.getId(),
                invitation.getInviterId(),
                invitation.getTargetEmail()
            )
        );

        log.info(
            "Invitation with ID {} has been declined.",
            command.invitationId()
        );
        return invitation.getId();
    }

    @Override
    @Transactional
    public InvitationId handle(AcceptInvitationCommand command) {
        var invitation = invitationRepository
            .findById(command.invitationId())
            .orElseThrow(() ->
                new InvitationNotFoundException(command.invitationId())
            );

        if (!invitation.getTargetEmail().equals(command.currentUserEmail())) {
            throw new InsufficientPermissionsException(
                "You do not have permission to accept this invitation."
            );
        }

        invitation.updateStatus(InvitationStatus.ACCEPTED);
        invitationRepository.save(invitation);

        eventPublisher.publishEvent(
            new InvitationAcceptedEvent(
                invitation.getId(),
                invitation.getInviterId(),
                invitation.getTargetEmail()
            )
        );

        log.info(
            "Invitation with ID {} has been accepted.",
            command.invitationId()
        );
        return invitation.getId();
    }
}
