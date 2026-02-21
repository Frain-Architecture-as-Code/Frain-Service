package com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.commands.AcceptInvitationCommand;
import com.frain.frainapi.organizations.domain.model.commands.DeclineInvitationCommand;
import com.frain.frainapi.organizations.domain.model.commands.SendInvitationCommand;
import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.organizations.interfaces.rest.controllers.requests.SendInvitationRequest;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;
import com.frain.frainapi.shared.domain.model.valueobjects.User;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;

public class InvitationCommandAssembler {

    public static SendInvitationCommand toSendInvitationCommandFromRequest(
            String organizationId,
            Member inviter,
            EmailAddress senderEmail,
            SendInvitationRequest request
    ) {
        return new SendInvitationCommand(
                OrganizationId.fromString(organizationId),
                inviter,
                request.targetEmail(),
                request.role(),
                senderEmail
        );
    }

    public static AcceptInvitationCommand toAcceptInvitationCommand(
            String invitationId,
            User user
    ) {
        return new AcceptInvitationCommand(
                InvitationId.fromString(invitationId),
                user
        );
    }

    public static DeclineInvitationCommand toDeclineInvitationCommand(
            String invitationId,
            EmailAddress currentUserEmail
    ) {
        return new DeclineInvitationCommand(
                InvitationId.fromString(invitationId),
                currentUserEmail
        );
    }
}
