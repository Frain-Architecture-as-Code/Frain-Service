package com.frain.frainapi.organizations.domain.model.valueobjects;

import com.frain.frainapi.organizations.domain.exceptions.InvalidMemberNameLengthException;
import com.frain.frainapi.organizations.domain.exceptions.MemberNameRequiredException;
import jakarta.persistence.Embeddable;

@Embeddable
public record MemberName(String value) {
    private static final int MAX_LENGTH = 30;
    private static final int MIN_LENGTH = 6;

    public MemberName {
        if (value == null || value.isBlank()) {
            throw new MemberNameRequiredException();
        }
        if (value.length() > MAX_LENGTH || value.length() < MIN_LENGTH) {
            throw new InvalidMemberNameLengthException(value.length(), MIN_LENGTH, MAX_LENGTH);
        }
    }
}
