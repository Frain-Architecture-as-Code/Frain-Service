package com.frain.frainapi.shared.domain.exceptions;

public class InsufficientPermissionsException extends RuntimeException {
    public InsufficientPermissionsException() {
        super("User does not have sufficient permissions to perform this action.");
    }
}
