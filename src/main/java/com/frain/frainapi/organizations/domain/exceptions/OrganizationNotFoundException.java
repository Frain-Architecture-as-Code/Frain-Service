package com.frain.frainapi.organizations.domain.exceptions;

import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;

public class OrganizationNotFoundException extends RuntimeException {
    public OrganizationNotFoundException(OrganizationId organizationId) {
        super(String.format("Organization with ID %s not found", organizationId));
    }
}
