package com.frain.frainapi.projects.domain.model.commands;

import com.frain.frainapi.projects.domain.model.valueobjects.C4Model;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;

/**
 * Command to update the entire C4Model of a project.
 * Used by the SDK to push a complete new model.
 */
public record UpdateC4ModelCommand(
        ProjectId projectId,
        C4Model c4Model
) {}
