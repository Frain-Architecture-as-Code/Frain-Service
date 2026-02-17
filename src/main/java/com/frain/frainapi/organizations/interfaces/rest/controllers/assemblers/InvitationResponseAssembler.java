package com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;
import com.frain.frainapi.organizations.interfaces.rest.controllers.responses.InvitationAcceptedResponse;
import com.frain.frainapi.organizations.interfaces.rest.controllers.responses.InvitationDeclinedResponse;

public class InvitationResponseAssembler {
    public static InvitationAcceptedResponse toInvitationAcceptedResponse(InvitationId invitationId) {
        return new InvitationAcceptedResponse(
                invitationId.toString()
        );
    }

    public static InvitationAcceptedResponse toInvitationAcceptedResponseFromString(String invitationId) {
        return new InvitationAcceptedResponse(invitationId);
    }

    public static InvitationDeclinedResponse toInvitationDeclinedResponse(InvitationId invitationId) {
        return new InvitationDeclinedResponse(
                invitationId.toString()
        );
    }

    public static InvitationDeclinedResponse toInvitationDeclinedResponseFromString(String invitationId) {
        return new InvitationDeclinedResponse(invitationId);
    }
}
