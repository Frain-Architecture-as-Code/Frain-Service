package com.frain.frainapi.organizations.domain.model.commands;

import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;

public record AcceptInvitationCommand(InvitationId invitationId, OrganizationId organizationId, EmailAddress currentUserEmail) {
}
