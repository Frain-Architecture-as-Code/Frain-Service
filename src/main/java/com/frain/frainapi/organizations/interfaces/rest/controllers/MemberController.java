package com.frain.frainapi.organizations.interfaces.rest.controllers;

import com.frain.frainapi.organizations.domain.model.commands.UpdateMemberCommand;
import com.frain.frainapi.organizations.domain.model.queries.GetMemberByUserIdAndOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetMembersByOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberId;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.organizations.domain.services.MemberCommandService;
import com.frain.frainapi.organizations.domain.services.MemberQueryService;
import com.frain.frainapi.organizations.domain.services.OrganizationQueryService;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.MemberAssembler;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.MemberCommandAssembler;
import com.frain.frainapi.organizations.interfaces.rest.controllers.requests.UpdateMemberRequest;
import com.frain.frainapi.organizations.interfaces.rest.controllers.responses.MemberResponse;
import com.frain.frainapi.shared.infrastructure.security.UserContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/members")
public class MemberController {
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    private final UserContext userContext;

    public MemberController(MemberCommandService memberCommandService, MemberQueryService memberQueryService, UserContext userContext) {
        this.memberCommandService = memberCommandService;
        this.memberQueryService = memberQueryService;
        this.userContext = userContext;
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers(@PathVariable OrganizationId organizationId) {
        var currentUserId = userContext.getCurrentUserId();
        var result = memberQueryService.handle(new GetMemberByUserIdAndOrganizationIdQuery(currentUserId, organizationId));

        if (result.isEmpty()) {
            throw new RuntimeException("Current user is not a member of the organization");
        }

        var query = new GetMembersByOrganizationIdQuery(organizationId);
        var memberList = memberQueryService.handle(query);

        var memberListResponse = MemberAssembler.toResponsesFromEntities(memberList);

        return ResponseEntity.ok(memberListResponse);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable OrganizationId organizationId, @PathVariable MemberId memberId, @RequestBody UpdateMemberRequest request) {
        var currentUserId = userContext.getCurrentUserId();
        var result = memberQueryService.handle(new GetMemberByUserIdAndOrganizationIdQuery(currentUserId, organizationId));

        if (result.isEmpty()) {
            throw new RuntimeException("Current user is not a member of the organization");
        }

        var currentMember = result.get();

        var command = MemberCommandAssembler.toUpdateMemberCommandFromRequest(request, memberId, currentMember);

        var targetMember = memberCommandService.handle(command);

        var memberResponse = MemberAssembler.toResponseFromEntity(targetMember);

        return ResponseEntity.ok(memberResponse);
    }

}
