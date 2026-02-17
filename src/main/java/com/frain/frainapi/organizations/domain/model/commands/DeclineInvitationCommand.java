package com.frain.frainapi.organizations.domain.model.commands;

import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;

public record DeclineInvitationCommand(InvitationId invitationId, EmailAddress currentUserEmail) {
}
