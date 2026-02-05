package com.frain.frainapi.organizations.domain.model.valueobjects;

import com.frain.frainapi.organizations.domain.exceptions.InvalidMemberNameLengthException;
import com.frain.frainapi.organizations.domain.exceptions.MemberNameRequiredException;
import jakarta.persistence.Embeddable;

@Embeddable
public record MemberName(String value) {
    public static final int MIN_LENGTH = 3;
    public static final int MAX_LENGTH = 50;

    public MemberName {
        if (value == null || value.isBlank()) {
            throw new MemberNameRequiredException();
        }
        if (value.length() > MAX_LENGTH || value.length() < MIN_LENGTH) {
            throw new InvalidMemberNameLengthException(value.length(), MIN_LENGTH, MAX_LENGTH);
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
