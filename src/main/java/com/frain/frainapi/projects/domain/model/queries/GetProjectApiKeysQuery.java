package com.frain.frainapi.projects.domain.model.queries;

import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;

/**
 * Query to get all API keys for a project.
 */
public record GetProjectApiKeysQuery(ProjectId projectId) {}
