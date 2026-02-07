package com.frain.frainapi.projects.domain.model.valueobjects;

import com.frain.frainapi.projects.domain.exceptions.InvalidProjectNameLengthException;
import com.frain.frainapi.projects.domain.exceptions.ProjectNameRequiredException;
import jakarta.persistence.Embeddable;

@Embeddable
public record ProjectName(String value) {
    public static final int MIN_LENGTH = 3;
    public static final int MAX_LENGTH = 50;

    public ProjectName {
        if (value == null || value.isBlank()) {
            throw new ProjectNameRequiredException();
        }
        if (value.length() > MAX_LENGTH || value.length() < MIN_LENGTH) {
            throw new InvalidProjectNameLengthException(value.length(), MIN_LENGTH, MAX_LENGTH);
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
