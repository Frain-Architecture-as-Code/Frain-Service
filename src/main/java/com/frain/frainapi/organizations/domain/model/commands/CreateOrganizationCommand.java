package com.frain.frainapi.organizations.domain.model.commands;

import com.frain.frainapi.organizations.domain.model.valueobjects.MemberName;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationName;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationVisibility;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;

public record CreateOrganizationCommand(OrganizationName name, OrganizationVisibility visibility, UserId ownerUserId, MemberName ownerName) {
}
