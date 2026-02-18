package com.frain.frainapi.organizations.interfaces.rest.controllers;

import com.frain.frainapi.organizations.domain.services.InvitationCommandService;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.InvitationCommandAssembler;
import com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers.InvitationResponseAssembler;
import com.frain.frainapi.organizations.interfaces.rest.controllers.responses.InvitationAcceptedResponse;
import com.frain.frainapi.shared.infrastructure.security.UserContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invitations")
@Tag(name = "Invitation Actions")
public class InvitationActionController {

    private final InvitationCommandService invitationCommandService;
    private final UserContext userContext;

    public InvitationActionController(
            InvitationCommandService invitationCommandService,
            UserContext userContext
    ) {
        this.invitationCommandService = invitationCommandService;
        this.userContext = userContext;
    }

    @PatchMapping("/{invitationId}/accept")
    public ResponseEntity<InvitationAcceptedResponse> acceptInvitation(@PathVariable String invitationId) {
        var user = userContext.getCurrentUser();

        var command = InvitationCommandAssembler.toAcceptInvitationCommand(
                invitationId,
                user
        );

        var resultInvitationId = invitationCommandService.handle(command);

        var response = InvitationResponseAssembler.toInvitationAcceptedResponse(resultInvitationId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{invitationId}/reject")
    public ResponseEntity<?> rejectInvitation(@PathVariable String invitationId) {
        var currentUserEmail = userContext.getCurrentUserEmail();

        var command = InvitationCommandAssembler.toDeclineInvitationCommand(
                invitationId,
                currentUserEmail
        );

        var resultInvitationId = invitationCommandService.handle(command);

        var response = InvitationResponseAssembler.toInvitationDeclinedResponse(resultInvitationId);

        return ResponseEntity.ok(response);
    }
}
