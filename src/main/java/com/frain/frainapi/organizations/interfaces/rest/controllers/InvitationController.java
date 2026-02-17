package com.frain.frainapi.organizations.interfaces.rest.controllers;

import com.frain.frainapi.organizations.domain.exceptions.InvitationNotFoundException;
import com.frain.frainapi.organizations.domain.model.queries.GetInvitationByIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetInvitationsByOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.organizations.domain.services.InvitationCommandService;
import com.frain.frainapi.organizations.domain.services.InvitationQueryService;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.InvitationAssembler;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.InvitationCommandAssembler;
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
    private final OrganizationContextUtils organizationContextUtils;
    private final UserContext userContext;

    public InvitationController(
            InvitationCommandService invitationCommandService,
            InvitationQueryService invitationQueryService,
            OrganizationContextUtils organizationContextUtils,
            UserContext userContext
    ) {
        this.invitationCommandService = invitationCommandService;
        this.invitationQueryService = invitationQueryService;
        this.organizationContextUtils = organizationContextUtils;
        this.userContext = userContext;
    }

    @GetMapping
    public ResponseEntity<List<InvitationResponse>> getInvitations(
        @PathVariable String organizationId
    ) {
        var currentMember = organizationContextUtils.validateUserBelongsToOrganization(organizationId);

        if (!currentMember.isOwner()) {
            throw new InsufficientPermissionsException(
                "Only owner can view invitations"
            );
        }

        var invitations = invitationQueryService.handle(
            new GetInvitationsByOrganizationIdQuery(OrganizationId.fromString(organizationId))
        );
        var invitationResponses =
            InvitationAssembler.toResponseListFromEntities(invitations);

        return ResponseEntity.ok(invitationResponses);
    }

    @PostMapping
    public ResponseEntity<InvitationResponse> sendInvitation(
        @PathVariable String organizationId,
        @RequestBody SendInvitationRequest request
    ) {

        var currentMember = organizationContextUtils.validateUserBelongsToOrganization(organizationId);

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
}
