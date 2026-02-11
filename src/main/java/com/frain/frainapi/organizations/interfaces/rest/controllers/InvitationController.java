package com.frain.frainapi.organizations.interfaces.rest.controllers;

import com.frain.frainapi.organizations.domain.exceptions.InvitationNotFoundException;
import com.frain.frainapi.organizations.domain.model.queries.GetInvitationByIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetInvitationsByOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetMemberByUserIdAndOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.organizations.domain.services.InvitationCommandService;
import com.frain.frainapi.organizations.domain.services.InvitationQueryService;
import com.frain.frainapi.organizations.domain.services.MemberQueryService;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.InvitationAssembler;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.InvitationCommandAssembler;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.InvitationResponseAssembler;
import com.frain.frainapi.organizations.interfaces.rest.controllers.requests.SendInvitationRequest;
import com.frain.frainapi.organizations.interfaces.rest.controllers.responses.InvitationResponse;
import com.frain.frainapi.shared.domain.exceptions.InsufficientPermissionsException;
import com.frain.frainapi.shared.infrastructure.security.UserContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/invitations")
@Tag(name = "Invitations")
public class InvitationController {

    private final InvitationCommandService invitationCommandService;
    private final InvitationQueryService invitationQueryService;
    private final MemberQueryService memberQueryService;

    private final UserContext userContext;

    public InvitationController(
        InvitationCommandService invitationCommandService,
        InvitationQueryService invitationQueryService,
        MemberQueryService memberQueryService,
        UserContext userContext
    ) {
        this.invitationCommandService = invitationCommandService;
        this.invitationQueryService = invitationQueryService;
        this.memberQueryService = memberQueryService;
        this.userContext = userContext;
    }

    @GetMapping
    public ResponseEntity<List<InvitationResponse>> getInvitations(
        @PathVariable OrganizationId organizationId
    ) {
        var currentUserId = userContext.getCurrentUserId();
        var result = memberQueryService.handle(
            new GetMemberByUserIdAndOrganizationIdQuery(
                currentUserId,
                organizationId
            )
        );

        if (result.isEmpty()) {
            throw new RuntimeException(
                "Current user is not a member of the organization"
            );
        }

        var currentMember = result.get();

        if (!currentMember.isOwner()) {
            throw new InsufficientPermissionsException(
                "Only owner can view invitations"
            );
        }

        var invitations = invitationQueryService.handle(
            new GetInvitationsByOrganizationIdQuery(organizationId)
        );
        var invitationResponses =
            InvitationAssembler.toResponseListFromEntities(invitations);

        return ResponseEntity.ok(invitationResponses);
    }

    @PostMapping
    public ResponseEntity<InvitationResponse> sendInvitation(
        @PathVariable OrganizationId organizationId,
        @RequestBody SendInvitationRequest request
    ) {
        var currentUserId = userContext.getCurrentUserId();
        var result = memberQueryService.handle(
            new GetMemberByUserIdAndOrganizationIdQuery(
                currentUserId,
                organizationId
            )
        );
        if (result.isEmpty()) {
            throw new RuntimeException(
                "Current user is not a member of the organization"
            );
        }
        var currentMember = result.get();

        var currentUserEmail = userContext.getCurrentUserEmail();

        var command =
            InvitationCommandAssembler.toSendInvitationCommandFromRequest(
                organizationId,
                currentMember,
                currentUserEmail,
                request
            );

        var invitationId = invitationCommandService.handle(command);

        var invitationResult = invitationQueryService.handle(
            new GetInvitationByIdQuery(invitationId)
        );

        if (invitationResult.isEmpty()) {
            throw new InvitationNotFoundException(invitationId);
        }
        var invitation = invitationResult.get();
        var invitationResponse = InvitationAssembler.toResponseFromEntity(
            invitation
        );
        return ResponseEntity.ok(invitationResponse);
    }

    @PatchMapping("/{invitationId}")
    public ResponseEntity<?> acceptInvitation(
        @PathVariable OrganizationId organizationId,
        @PathVariable InvitationId invitationId
    ) {
        var currentUserEmail = userContext.getCurrentUserEmail();

        var command =
            InvitationCommandAssembler.toAcceptInvitationCommandFromIds(
                organizationId,
                invitationId,
                currentUserEmail
            );

        invitationCommandService.handle(command);

        var invitationResult =
            InvitationResponseAssembler.toInvitationAcceptedResponse(
                invitationId
            );

        return ResponseEntity.ok(invitationResult);
    }

    @DeleteMapping("/{invitationId}")
    public ResponseEntity<?> declineInvitation(
        @PathVariable OrganizationId organizationId,
        @PathVariable InvitationId invitationId
    ) {
        var currentUserEmail = userContext.getCurrentUserEmail();

        var command =
            InvitationCommandAssembler.toDeclineInvitationCommandFromIds(
                organizationId,
                invitationId,
                currentUserEmail
            );
        invitationCommandService.handle(command);

        var invitationResult =
            InvitationResponseAssembler.toInvitationDeclinedResponse(
                invitationId
            );
        return ResponseEntity.ok(invitationResult);
    }
}
