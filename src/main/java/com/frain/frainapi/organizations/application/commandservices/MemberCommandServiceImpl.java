package com.frain.frainapi.organizations.application.commandservices;

import com.frain.frainapi.organizations.domain.exceptions.MemberNotFoundException;
import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.commands.EnrollMemberCommand;
import com.frain.frainapi.organizations.domain.model.commands.UpdateMemberCommand;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberId;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberName;
import com.frain.frainapi.organizations.domain.services.MemberCommandService;
import com.frain.frainapi.organizations.infrastructure.repositories.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;

    public MemberCommandServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    @Transactional
    public Member handle(UpdateMemberCommand command) {
        var targetMember = memberRepository.findById(command.targetMemberId())
                .orElseThrow(() -> new MemberNotFoundException(command.targetMemberId()));

        if (command.performedBy().isOwner()) {
            targetMember.updateMember(command.newName(), command.newRole());
        } else if (command.performedBy().getId() == command.targetMemberId()) {
            targetMember.updateMember(command.newName(), null);
        }

        memberRepository.save(targetMember);
        log.info("Member with ID {} has been updated.", command.targetMemberId());

        return targetMember;
    }

    @Override
    public MemberId handle(EnrollMemberCommand command) {
        var memberId = MemberId.generate();
        var newMember = new Member(
                memberId,
                command.organizationId(),
                command.userId(),
                new MemberName(command.userName().toString()),
                command.role()
        );

        memberRepository.save(newMember);
        log.info(String.format("User %s has been enrolled to organization with ID %s as %s",
                command.userName(),
                command.organizationId(),
                command.role().toString()
        ));
        return memberId;
    }
}
