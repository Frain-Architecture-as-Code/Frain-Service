package com.frain.frainapi.organizations.domain.model.commands;

import com.frain.frainapi.organizations.domain.model.valueobjects.MemberRole;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;

public record SendInvitationCommand(OrganizationId organizationId, EmailAddress targetEmail, MemberRole role) {
}
