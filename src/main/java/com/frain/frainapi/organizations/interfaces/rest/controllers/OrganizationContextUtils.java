package com.frain.frainapi.organizations.interfaces.rest.controllers;

import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.services.MemberQueryService;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.MemberQueryAssembler;
import com.frain.frainapi.shared.infrastructure.security.UserContext;
import org.springframework.stereotype.Component;

@Component
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
            throw new RuntimeException("Current user is not a member of the organization");
        }

        return result.get();
    }
}
