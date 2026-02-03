package com.frain.frainapi.organizations.domain.model.valueobjects;

import com.frain.frainapi.organizations.domain.exceptions.InvalidOrganizationNameLengthException;
import com.frain.frainapi.organizations.domain.exceptions.OrganizationNameRequiredException;
import jakarta.persistence.Embeddable;

@Embeddable
public record OrganizationName(String value) {
    public static final Integer MAX_LENGTH = 50;
    public static final Integer MIN_LENGTH = 8;

    public OrganizationName {
        if (value == null || value.isBlank()) {
            throw new OrganizationNameRequiredException();
        }
        if (value.length() > MAX_LENGTH || value.length() < MIN_LENGTH) {
            throw new InvalidOrganizationNameLengthException(value.length(), MIN_LENGTH, MAX_LENGTH);
        }
    }
}
