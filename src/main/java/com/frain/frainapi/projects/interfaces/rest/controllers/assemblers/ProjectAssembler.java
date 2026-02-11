package com.frain.frainapi.projects.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.projects.domain.model.Project;
import com.frain.frainapi.projects.interfaces.rest.controllers.responses.ProjectResponse;

public class ProjectAssembler {
    public static ProjectResponse toResponseFromEntity(Project project) {
        return new ProjectResponse(
                project.getId().toString(),
                project.getOrganizationId().toString(),
                project.getVisibility().name(),
                project.getCreatedAt().toString(),
                project.getUpdatedAt().toString()
        );
    }
}
