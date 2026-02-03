package com.frain.frainapi.shared.domain.exceptions;

public class StringLengthException extends RuntimeException {
    public StringLengthException(int currentLength, int min, int max, String fieldName) {
        super(String.format(
                "%s must be between %d and %d characters length. (Actual: %d)", fieldName,
                min, max, currentLength
        ));
    }
}
