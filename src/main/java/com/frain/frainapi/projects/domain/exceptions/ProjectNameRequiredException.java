package com.frain.frainapi.projects.domain.exceptions;

public class ProjectNameRequiredException extends RuntimeException {
    public ProjectNameRequiredException() {
        super("Project name is required and cannot be null or blank.");
    }
}
