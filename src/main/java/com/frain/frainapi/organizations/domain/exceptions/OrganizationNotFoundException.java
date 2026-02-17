package com.frain.frainapi.organizations.domain.exceptions;

public class OrganizationNotFoundException extends RuntimeException {
    public OrganizationNotFoundException(String organizationId) {
        super(String.format("Organization with ID %s not found", organizationId));
    }
}
