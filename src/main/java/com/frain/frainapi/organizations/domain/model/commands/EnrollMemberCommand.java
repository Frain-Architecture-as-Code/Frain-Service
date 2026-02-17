package com.frain.frainapi.organizations.domain.model.commands;

import com.frain.frainapi.organizations.domain.model.valueobjects.MemberRole;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;
import com.frain.frainapi.shared.domain.model.valueobjects.UserName;

public record EnrollMemberCommand(UserId userId, OrganizationId organizationId, UserName userName, MemberRole role) {
}
