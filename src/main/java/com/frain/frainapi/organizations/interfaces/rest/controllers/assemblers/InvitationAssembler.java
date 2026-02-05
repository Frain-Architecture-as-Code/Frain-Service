package com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.organizations.interfaces.rest.controllers.responses.InvitationResponse;

import java.util.List;

public class InvitationAssembler {

    public static InvitationResponse toResponseFromEntity(com.frain.frainapi.organizations.domain.model.Invitation invitation) {
        return new InvitationResponse(
                invitation.getId().toString(),
                invitation.getTargetEmail().toString(),
                invitation.getStatus().toString(),
                invitation.getRole().toString(),
                invitation.getOrganizationId().toString(),
                invitation.getInviterId().toString(),
                invitation.getCreatedAt().toString(),
                invitation.getUpdatedAt().toString()
        );
    }

    public static List<InvitationResponse> toResponseListFromEntities(List<com.frain.frainapi.organizations.domain.model.Invitation> invitations) {
        return invitations.stream()
                .map(InvitationAssembler::toResponseFromEntity)
                .toList();
    }
}
