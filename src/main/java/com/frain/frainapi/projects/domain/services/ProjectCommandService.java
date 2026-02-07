package com.frain.frainapi.projects.domain.services;

import com.frain.frainapi.projects.domain.model.commands.CreateProjectCommand;
import com.frain.frainapi.projects.domain.model.commands.UpdateProjectVisibilityCommand;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;

public interface ProjectCommandService {
    ProjectId handle(CreateProjectCommand command);
    ProjectId handle(UpdateProjectVisibilityCommand command);
}
