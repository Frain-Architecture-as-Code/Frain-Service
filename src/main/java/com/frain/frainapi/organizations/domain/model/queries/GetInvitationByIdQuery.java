package com.frain.frainapi.organizations.domain.model.queries;

import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;

public record GetInvitationByIdQuery(InvitationId invitationId) {
}
