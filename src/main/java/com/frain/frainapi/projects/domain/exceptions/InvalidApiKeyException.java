package com.frain.frainapi.projects.domain.exceptions;

public class InvalidApiKeyException extends RuntimeException {
    public InvalidApiKeyException() {
        super("Invalid or expired API key");
    }

    public InvalidApiKeyException(String message) {
        super(message);
    }
}
