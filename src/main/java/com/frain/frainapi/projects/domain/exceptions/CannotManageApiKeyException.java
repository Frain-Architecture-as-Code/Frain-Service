package com.frain.frainapi.projects.domain.exceptions;

public class CannotManageApiKeyException extends RuntimeException {
    public CannotManageApiKeyException(String message) {
        super(message);
    }

    public static CannotManageApiKeyException insufficientRole() {
        return new CannotManageApiKeyException("You do not have permission to manage API keys");
    }

    public static CannotManageApiKeyException cannotManageHigherRole() {
        return new CannotManageApiKeyException("You cannot manage API keys for members with equal or higher role");
    }

    public static CannotManageApiKeyException contributorCannotCreate() {
        return new CannotManageApiKeyException("Contributors cannot create API keys");
    }
}
