package com.frain.frainapi.organizations.domain.model.commands;

import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;
import com.frain.frainapi.shared.domain.model.valueobjects.User;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;

public record AcceptInvitationCommand(InvitationId invitationId, User performBy) {
}
