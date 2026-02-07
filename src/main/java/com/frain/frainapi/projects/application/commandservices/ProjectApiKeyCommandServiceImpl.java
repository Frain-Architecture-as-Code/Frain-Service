package com.frain.frainapi.projects.application.commandservices;

import com.frain.frainapi.projects.domain.model.commands.CreateProjectApiKeyCommand;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectApiKeyId;
import com.frain.frainapi.projects.domain.services.ProjectApiKeyCommandService;
import org.springframework.stereotype.Service;

@Service
public class ProjectApiKeyCommandServiceImpl implements ProjectApiKeyCommandService {
    @Override
    public ProjectApiKeyId handle(CreateProjectApiKeyCommand command) {
        return null;
    }
}
