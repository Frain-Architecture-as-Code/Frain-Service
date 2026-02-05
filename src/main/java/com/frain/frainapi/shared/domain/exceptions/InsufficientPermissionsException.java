package com.frain.frainapi.shared.domain.exceptions;

public class InsufficientPermissionsException extends RuntimeException {
    public InsufficientPermissionsException(String message) {
        super(String.format("User does not have sufficient permissions to perform this action: %s", message));
    }
}
