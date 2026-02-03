package com.frain.frainapi.organizations.domain.model.commands;

import com.frain.frainapi.organizations.domain.model.valueobjects.MemberId;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberRole;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;

public record ChangeMemberRoleCommand(OrganizationId organizationId, MemberId memberId, MemberRole newRole, MemberId performedBy) {
}
