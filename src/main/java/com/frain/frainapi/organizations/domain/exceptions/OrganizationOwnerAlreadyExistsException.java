package com.frain.frainapi.organizations.domain.exceptions;

import com.frain.frainapi.shared.domain.exceptions.DomainRuleException;

public class OrganizationOwnerAlreadyExistsException extends DomainRuleException {
    public OrganizationOwnerAlreadyExistsException(String organizationId) {
        super("Organization " + organizationId + " already has an owner. Only one owner is allowed.");
    }
}
