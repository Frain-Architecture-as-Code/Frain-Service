package com.frain.frainapi.organizations.domain.exceptions;

import com.frain.frainapi.shared.domain.exceptions.StringLengthException;

public class InvalidOrganizationNameLengthException extends StringLengthException {
    public InvalidOrganizationNameLengthException(int currentLength, int min, int max) {
        super(currentLength, min, max, InvalidOrganizationNameLengthException.class.getName());
    }
}
