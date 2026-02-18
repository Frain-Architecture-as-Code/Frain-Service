package com.frain.frainapi.organizations.interfaces.rest.controllers;

import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.services.MemberQueryService;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.MemberQueryAssembler;
import com.frain.frainapi.shared.infrastructure.security.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrganizationContextUtils {

    private final UserContext userContext;
    private final MemberQueryService memberQueryService;

    public OrganizationContextUtils(UserContext userContext, MemberQueryService memberQueryService) {
        this.userContext = userContext;
        this.memberQueryService = memberQueryService;
    }

    public Member validateUserBelongsToOrganization(String organizationId) {
        var currentUserId = userContext.getCurrentUserId();
        var result = memberQueryService.handle(MemberQueryAssembler.toGetMemberByUserIdAndOrganizationIdQueryFromString(currentUserId, organizationId));

        if (result.isEmpty()) {
            log.error("User {} does not belong to organization {}", currentUserId, organizationId);
            throw new RuntimeException("Current user is not a member of the organization");
        }

        log.info("User {} belongs to organization {}, member id: {}",
            currentUserId,
            organizationId,
            result.get().getId()
        );
        return result.get();
    }

    public Member getMemberById(String memberId) {
        var result = memberQueryService.handle(MemberQueryAssembler.toGetMemberByIdQuery(memberId));

        if (result.isEmpty()) {
            log.error("Member with id {} not found", memberId);
            throw new RuntimeException("Member not found");
        }

        return result.get();

    }
}
