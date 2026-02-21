package com.frain.frainapi.organizations.interfaces.rest.controllers.requests;

import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationVisibility;

public record CreateOrganizationRequest(String name, OrganizationVisibility visibility) {
}
