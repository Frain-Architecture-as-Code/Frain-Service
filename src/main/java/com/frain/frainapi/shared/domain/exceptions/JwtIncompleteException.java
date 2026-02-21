package com.frain.frainapi.shared.domain.exceptions;

public class JwtIncompleteException extends RuntimeException {
    public JwtIncompleteException(String message) {
        super(message);
    }
}
