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

    public InvitationCommandServiceImpl(OrganizationRepository organizationRepository, MemberRepository memberRepository, InvitationRepository invitationRepository, ApplicationEventPublisher eventPublisher) {
        this.organizationRepository = organizationRepository;
        this.memberRepository = memberRepository;
        this.invitationRepository = invitationRepository;
        this.eventPublisher = eventPublisher;
    }


    @Override
    @Transactional
    public void handle(SendInvitationCommand command) {

        var performingMember = memberRepository.findById(command.performBy()).orElseThrow(() -> new MemberNotFoundException(command.performBy()));

        var targetOrganization = organizationRepository.findById(command.organizationId()).orElseThrow(() -> new OrganizationNotFoundException(command.organizationId()));

        if (!performingMember.canInvitePeople()) {
            throw new InsufficientPermissionsException();
        }

        var invitation = new Invitation(InvitationId.generate(), command.organizationId(), command.performBy(), command.targetEmail(), command.role());

        invitationRepository.save(invitation);

        eventPublisher.publishEvent(new InvitationSentEvent(invitation.getId(), invitation.getInviterId(), invitation.getTargetEmail(), targetOrganization.getName()));

        log.info("Invitation with ID {} has been sent to {}.", invitation.getInviterId(), command.targetEmail());
    }

    @Override
    @Transactional
    public void handle(DeclineInvitationCommand command) {
        var invitation = invitationRepository.findById(command.invitationId()).orElseThrow(() -> new InvitationNotFoundException(command.invitationId()));

        invitation.updateStatus(InvitationStatus.DECLINED);

        invitationRepository.save(invitation);

        eventPublisher.publishEvent(new InvitationDeclinedEvent(invitation.getId(), invitation.getInviterId(), invitation.getTargetEmail()));


        log.info("Invitation with ID {} has been declined.", command.invitationId());

    }

    @Override
    @Transactional
    public void handle(AcceptInvitationCommand command) {
        var invitation = invitationRepository.findById(command.invitationId()).orElseThrow(() -> new InvitationNotFoundException(command.invitationId()));

        invitation.updateStatus(InvitationStatus.ACCEPTED);
        invitationRepository.save(invitation);

        eventPublisher.publishEvent(new InvitationAcceptedEvent(invitation.getId(), invitation.getInviterId(), invitation.getTargetEmail()));

        log.info("Invitation with ID {} has been accepted.", command.invitationId());
    }
}
