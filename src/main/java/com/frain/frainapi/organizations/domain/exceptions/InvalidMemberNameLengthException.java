package com.frain.frainapi.organizations.domain.exceptions;

import com.frain.frainapi.shared.domain.exceptions.StringLengthException;

public class InvalidMemberNameLengthException extends StringLengthException {
    public InvalidMemberNameLengthException(int currentLength, int min, int max) {
        super(currentLength, min, max, InvalidMemberNameLengthException.class.getName());
    }
}
