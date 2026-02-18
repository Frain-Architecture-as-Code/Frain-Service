package com.frain.frainapi.organizations.domain.model.commands;

import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;

public record DeleteOrganizationCommand(OrganizationId organizationId, Member requestingMember) {
}
