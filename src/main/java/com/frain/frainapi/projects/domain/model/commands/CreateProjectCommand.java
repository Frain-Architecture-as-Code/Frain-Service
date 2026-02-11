package com.frain.frainapi.projects.domain.model.commands;

import com.frain.frainapi.projects.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectVisibility;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;

public record CreateProjectCommand(OrganizationId organizationId, UserId userId, ProjectVisibility visibility) {
}
