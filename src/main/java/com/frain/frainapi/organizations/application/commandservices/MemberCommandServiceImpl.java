package com.frain.frainapi.organizations.application.commandservices;

import com.frain.frainapi.organizations.domain.exceptions.MemberNotFoundException;
import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.commands.EnrollMemberCommand;
import com.frain.frainapi.organizations.domain.model.commands.KickMemberFromOrganizationCommand;
import com.frain.frainapi.organizations.domain.model.commands.UpdateMemberCommand;
import com.frain.frainapi.organizations.domain.model.queries.GetMemberByIdQuery;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberId;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberName;
import com.frain.frainapi.organizations.domain.services.MemberCommandService;
import com.frain.frainapi.organizations.domain.services.MemberQueryService;
import com.frain.frainapi.organizations.infrastructure.repositories.MemberRepository;
import com.frain.frainapi.shared.domain.exceptions.InsufficientPermissionsException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final MemberQueryService memberQueryService;

    public MemberCommandServiceImpl(MemberRepository memberRepository, MemberQueryService memberQueryService) {
        this.memberRepository = memberRepository;
        this.memberQueryService = memberQueryService;
    }


    @Override
    @Transactional
    public Member handle(UpdateMemberCommand command) {
        var targetMember = memberRepository.findById(command.targetMemberId())
                .orElseThrow(() -> new MemberNotFoundException(command.targetMemberId()));

        if (!targetMember.getName().equals(command.newName())) {
            var existingMemberWithName = memberRepository.existsMemberByName(command.newName());
            if (existingMemberWithName) {
                throw new IllegalArgumentException(String.format("A member with name %s already exists in the organization.", command.newName()));
            }
        }

        if (command.performedBy().isOwner()) {
            targetMember.updateMember(command.newName(), command.newRole());
        } else if (command.performedBy().getId().equals(command.targetMemberId())) {
            log.info("Updating name of member with ID {} by themselves", command.targetMemberId());
            targetMember.updateMember(command.newName(), null);
        }

        memberRepository.save(targetMember);
        log.info("Member with ID {} has been updated.", command.targetMemberId());

        return targetMember;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public MemberId handle(EnrollMemberCommand command) {
        var memberId = MemberId.generate();
        var newMember = new Member(
                memberId,
                command.organizationId(),
                command.userId(),
                new MemberName(command.userName().toString()),
                command.role(), command.picture()
        );

        memberRepository.save(newMember);
        log.info("User %s has been enrolled to organization with ID %s as %s",
                command.userName(),
                command.organizationId(),
                command.role().toString()
        );
        return memberId;
    }

    @Override
    public MemberId handle(KickMemberFromOrganizationCommand command) {

        var result = memberQueryService.handle(new GetMemberByIdQuery(command.memberToKickId()));

        if (result.isEmpty()) {
            throw new MemberNotFoundException(command.memberToKickId());
        }

        var targetMember = result.get();

        if (!command.performBy().canKickMember(targetMember.getRole())) {
            throw new InsufficientPermissionsException(String.format("You don't have permissions to kick member with role %s", targetMember.getRole().toString()));
        }

        var kickedMemberId = targetMember.getId();

        memberRepository.delete(targetMember);

        log.info(String.format("Member with ID %s has been kicked from organization with ID %s by member with ID %s",
                targetMember.getId(),
                targetMember.getOrganizationId(),
                command.performBy().getId()
        ));

        return kickedMemberId;
    }
}
