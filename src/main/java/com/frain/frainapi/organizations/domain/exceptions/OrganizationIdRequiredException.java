package com.frain.frainapi.organizations.domain.exceptions;

import com.frain.frainapi.shared.domain.exceptions.DomainRuleException;

public class OrganizationIdRequiredException extends DomainRuleException {
    public OrganizationIdRequiredException() {
        super("Organization Id cannot be null");
    }
}
