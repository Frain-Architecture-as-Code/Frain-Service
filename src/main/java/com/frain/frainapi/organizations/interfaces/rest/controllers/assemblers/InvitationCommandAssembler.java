package com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.commands.AcceptInvitationCommand;
import com.frain.frainapi.organizations.domain.model.commands.DeclineInvitationCommand;
import com.frain.frainapi.organizations.domain.model.commands.SendInvitationCommand;
import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.organizations.interfaces.rest.controllers.requests.SendInvitationRequest;

public class InvitationCommandAssembler {

    public static SendInvitationCommand toSendInvitationCommandFromRequest(OrganizationId organizationId, Member inviter, SendInvitationRequest request) {
        return new SendInvitationCommand(organizationId, inviter, request.targetEmail() , request.role());
    }

    public static AcceptInvitationCommand toAcceptInvitationCommandFromIds(OrganizationId organizationId, InvitationId invitationId, Member currentMember) {
        return new AcceptInvitationCommand(invitationId, organizationId, currentMember);
    }

    public static DeclineInvitationCommand toDeclineInvitationCommandFromIds(OrganizationId organizationId, InvitationId invitationId, Member currentMember) {
        return new DeclineInvitationCommand(invitationId, organizationId, currentMember);
    }
}
