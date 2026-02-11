package com.frain.frainapi.projects.domain.services;

import com.frain.frainapi.projects.domain.model.commands.CreateProjectApiKeyCommand;
import com.frain.frainapi.projects.domain.model.commands.RecordApiKeyUsageCommand;
import com.frain.frainapi.projects.domain.model.commands.RevokeProjectApiKeyCommand;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectApiKeyId;

public interface ProjectApiKeyCommandService {
    /**
     * Creates a new API key for a project member.
     * @return The ID of the newly created API key
     */
    ProjectApiKeyId handle(CreateProjectApiKeyCommand command);

    /**
     * Revokes (deletes) an API key.
     */
    void handle(RevokeProjectApiKeyCommand command);

    /**
     * Records when an API key was last used for authentication.
     */
    void handle(RecordApiKeyUsageCommand command);
}
