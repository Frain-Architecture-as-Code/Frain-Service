package com.frain.frainapi.organizations.domain.exceptions;

import com.frain.frainapi.shared.domain.exceptions.DomainRuleException;

public class MemberNameRequiredException extends DomainRuleException {
    public MemberNameRequiredException() {
        super("MemberName cannot be null or empty");
    }
}
