package com.frain.frainapi.organizations.domain.exceptions;

import com.frain.frainapi.shared.domain.exceptions.DomainRuleException;

public class OrganizationNameRequiredException extends DomainRuleException {
    public OrganizationNameRequiredException() {
        super("OrganizationName cannot be null or empty");
    }
}
