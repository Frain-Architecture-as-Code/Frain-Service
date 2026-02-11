package com.frain.frainapi.projects.domain.model.commands;

import com.frain.frainapi.projects.domain.model.valueobjects.ProjectApiKeyId;

/**
 * Command to record when an API key was last used.
 * 
 * @param apiKeyId The ID of the API key that was used
 */
public record RecordApiKeyUsageCommand(
        ProjectApiKeyId apiKeyId
) {}
