package com.frain.frainapi.organizations.domain.model.commands;

import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;

public record AcceptInvitationCommand(InvitationId invitationId) {
}
