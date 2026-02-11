package com.frain.frainapi.projects.domain.exceptions;

import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(ProjectId projectId) {
        super(String.format("Project not found with ID: %s", projectId));
    }
}
