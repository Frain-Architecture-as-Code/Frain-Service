package com.frain.frainapi.organizations.domain.model.commands;

import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationName;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationVisibility;

public record CreateOrganizationCommand(OrganizationName name, OrganizationVisibility visibility) {
}
