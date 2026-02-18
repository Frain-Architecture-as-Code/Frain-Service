package com.frain.frainapi.organizations.interfaces.rest.controllers;

import com.frain.frainapi.organizations.domain.model.queries.GetMemberByUserIdAndOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetMembersByOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberId;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.organizations.domain.services.MemberCommandService;
import com.frain.frainapi.organizations.domain.services.MemberQueryService;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.MemberAssembler;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.MemberCommandAssembler;
import com.frain.frainapi.organizations.interfaces.rest.controllers.requests.UpdateMemberRequest;
import com.frain.frainapi.organizations.interfaces.rest.controllers.responses.MemberResponse;
import com.frain.frainapi.shared.domain.exceptions.InsufficientPermissionsException;
import com.frain.frainapi.shared.infrastructure.security.UserContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/members")
@Tag(name = "Member")
public class MemberController {
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;
    private final OrganizationContextUtils organizationContextUtils;

    public MemberController(MemberCommandService memberCommandService, MemberQueryService memberQueryService, OrganizationContextUtils organizationContextUtils) {
        this.memberCommandService = memberCommandService;
        this.memberQueryService = memberQueryService;
        this.organizationContextUtils = organizationContextUtils;
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers(@PathVariable String organizationId) {
        organizationContextUtils.validateUserBelongsToOrganization(organizationId);

        var query = new GetMembersByOrganizationIdQuery(OrganizationId.fromString(organizationId));
        var memberList = memberQueryService.handle(query);

        var memberListResponse = MemberAssembler.toResponseListFromEntities(memberList);

        return ResponseEntity.ok(memberListResponse);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponse> updateMember(@PathVariable String organizationId, @PathVariable String memberId, @RequestBody UpdateMemberRequest request) {

        var currentMember = organizationContextUtils.validateUserBelongsToOrganization(organizationId);

        var command = MemberCommandAssembler.toUpdateMemberCommandFromRequest(request, memberId, currentMember);

        var targetMember = memberCommandService.handle(command);

        var memberResponse = MemberAssembler.toResponseFromEntity(targetMember);

        return ResponseEntity.ok(memberResponse);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<MemberId> kickMember(@PathVariable String organizationId, @PathVariable String memberId) {
        var currentMember = organizationContextUtils.validateUserBelongsToOrganization(organizationId);

        var command = MemberCommandAssembler.toKickMemberFromOrganizationCommand(organizationId, memberId, currentMember);

        var kickedMemberId = memberCommandService.handle(command);

        return ResponseEntity.ok(kickedMemberId);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable String organizationId, @PathVariable String memberId) {
        var currentMember = organizationContextUtils.validateUserBelongsToOrganization(organizationId);

        var response = MemberAssembler.toResponseFromEntity(currentMember);

        return ResponseEntity.ok(response);
    }

}
