package com.frain.frainapi.shared.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import org.apache.commons.validator.routines.EmailValidator;

@Embeddable
public record EmailAddress(String value) {
    public EmailAddress {
        if (value == null) {
            throw new IllegalArgumentException("EmailAddress cannot be null or empty");
        }
        if (!EmailValidator.getInstance().isValid(value)) {
            throw new IllegalArgumentException("Invalid format for email: " + value);
        }
    }

    @JsonValue
    public String toString() {
        return value;
    }
}
