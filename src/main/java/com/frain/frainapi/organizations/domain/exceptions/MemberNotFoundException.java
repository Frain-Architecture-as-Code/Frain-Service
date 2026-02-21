package com.frain.frainapi.organizations.domain.exceptions;

import com.frain.frainapi.organizations.domain.model.valueobjects.MemberId;
import com.frain.frainapi.shared.domain.exceptions.DomainRuleException;

public class MemberNotFoundException extends DomainRuleException {
    public MemberNotFoundException(MemberId memberId) {
        super(String.format("Member with ID %s not found.", memberId.toString()));
    }
}
