package com.frain.frainapi.projects.domain.exceptions;

public class InvalidProjectNameLengthException extends RuntimeException {
    public InvalidProjectNameLengthException(int actualLength, int minLength, int maxLength) {
        super(String.format("Project name length must be between %d and %d characters. Actual length: %d.", minLength, maxLength, actualLength));
    }
}
