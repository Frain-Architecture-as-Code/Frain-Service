package com.frain.frainapi.shared.domain.model.valueobjects;

import com.frain.frainapi.shared.domain.exceptions.StringLengthException;

public record UserName(String value) {
    public static final int MIN_LENGTH = 3;
    public static final int MAX_LENGTH = 50;

    public UserName {
        if (value == null || value.isBlank()) {
            assert value != null;
            throw new RuntimeException("UserName cannot be null or blank");
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new StringLengthException(value.length(), MIN_LENGTH, MAX_LENGTH, "UserName");
        }
    }
}
