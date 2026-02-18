package com.frain.frainapi.organizations.application.commandservices;

import com.frain.frainapi.organizations.domain.exceptions.OrganizationNotFoundException;
import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.Organization;
import com.frain.frainapi.organizations.domain.model.commands.CreateOrganizationCommand;
import com.frain.frainapi.organizations.domain.model.commands.DeleteOrganizationCommand;
import com.frain.frainapi.organizations.domain.model.commands.UpdateOrganizationCommand;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberId;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberRole;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.organizations.domain.services.OrganizationCommandService;
import com.frain.frainapi.organizations.infrastructure.repositories.InvitationRepository;
import com.frain.frainapi.organizations.infrastructure.repositories.MemberRepository;
import com.frain.frainapi.organizations.infrastructure.repositories.OrganizationRepository;
import com.frain.frainapi.shared.domain.exceptions.InsufficientPermissionsException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrganizationCommandServiceImpl implements OrganizationCommandService {

    private final OrganizationRepository organizationRepository;
    private final MemberRepository memberRepository;
    private final InvitationRepository invitationRepository;

    public OrganizationCommandServiceImpl(OrganizationRepository organizationRepository, MemberRepository memberRepository, InvitationRepository invitationRepository) {
        this.organizationRepository = organizationRepository;
        this.memberRepository = memberRepository;
        this.invitationRepository = invitationRepository;
    }


    @Override
    @Transactional
    public void handle(UpdateOrganizationCommand command) {

        var organization = organizationRepository.findById(command.organizationId()).orElseThrow(() -> new OrganizationNotFoundException(command.organizationId().toString()));

        organization.updateDetails(command.name(), command.visibility());

        organizationRepository.save(organization);

        log.info("Organization with ID {} has been updated.", command.organizationId());
    }

    @Override
    @Transactional
    public OrganizationId handle(CreateOrganizationCommand command) {

        var organizationId = OrganizationId.generate();

        var member = new Member(MemberId.generate(), organizationId, command.ownerUserId(), command.ownerName(), MemberRole.OWNER, command.ownerPictureUrl());
        var organization = new Organization(organizationId, command.name(), member.getId(), command.visibility());

        organizationRepository.save(organization);
        memberRepository.save(member);

        log.info("Organization with ID {} has been created.", organizationId);
        log.info("Member with ID {} has been enrolled as OWNER.", member.getId());

        return organization.getId();
    }

    @Override
    @Transactional
    public OrganizationId handle(DeleteOrganizationCommand command) {

        if (!command.requestingMember().isOwner()) {
            throw new InsufficientPermissionsException(String.format("Member with ID %s does not have permission to delete organization with ID %s", command.requestingMember().getId(), command.organizationId()));
        }

        organizationRepository.deleteById(command.organizationId());
        log.info(String.format("Organization with ID %s has been deleted.", command.organizationId()));

        memberRepository.deleteAllByOrganizationId(command.organizationId());
        log.info(String.format("All members of organization with ID %s have been deleted.", command.organizationId()));

        invitationRepository.deleteAllByOrganizationId(command.organizationId());
        log.info(String.format("All invitations of organization with ID %s have been deleted.", command.organizationId()));

        return command.organizationId();
    }
}
