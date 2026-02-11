package com.frain.frainapi.projects.interfaces.rest.controllers.requests;

import com.frain.frainapi.projects.domain.model.valueobjects.ProjectVisibility;

public record CreateProjectRequest(ProjectVisibility visibility) {
}
