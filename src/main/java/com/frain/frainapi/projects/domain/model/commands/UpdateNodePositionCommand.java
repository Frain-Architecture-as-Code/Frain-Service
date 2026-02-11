package com.frain.frainapi.projects.domain.model.commands;

import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;

/**
 * Command to update a node's position within a view.
 */
public record UpdateNodePositionCommand(
        ProjectId projectId,
        String viewId,
        String nodeId,
        int x,
        int y
) {}
