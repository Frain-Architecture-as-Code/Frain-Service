package com.frain.frainapi.projects.domain.services;

import com.frain.frainapi.projects.domain.model.commands.CreateProjectApiKeyCommand;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectApiKeyId;

public interface ProjectApiKeyCommandService {
    ProjectApiKeyId handle(CreateProjectApiKeyCommand command);
}
