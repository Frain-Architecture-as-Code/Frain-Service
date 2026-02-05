package com.frain.frainapi.organizations.domain.model.commands;

import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationName;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationVisibility;

public record UpdateOrganizationCommand(OrganizationId organizationId, OrganizationName name, OrganizationVisibility visibility, Member requestingMember) {
}
