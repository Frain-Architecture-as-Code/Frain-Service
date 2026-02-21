package com.frain.frainapi.projects.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.projects.domain.model.commands.CreateProjectCommand;
import com.frain.frainapi.projects.domain.model.commands.UpdateProjectVisibilityCommand;
import com.frain.frainapi.projects.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectVisibility;
import com.frain.frainapi.projects.interfaces.rest.controllers.requests.CreateProjectRequest;
import com.frain.frainapi.projects.interfaces.rest.controllers.requests.UpdateProjectVisibilityRequest;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;

public class ProjectCommandAssembler {
    public static CreateProjectCommand toCreateProjectCommandFromRequest(CreateProjectRequest request, UserId userId, OrganizationId organizationId) {
        return new CreateProjectCommand(organizationId, userId, request.visibility());
    }

    public static CreateProjectCommand toCreateProjectCommandFromRequestAndString(CreateProjectRequest request, UserId userId, String organizationId) {
        return new CreateProjectCommand(OrganizationId.fromString(organizationId), userId, request.visibility());
    }

    public static UpdateProjectVisibilityCommand toUpdateProjectVisibilityCommandFromRequest(OrganizationId organizationId, ProjectId projectId, UpdateProjectVisibilityRequest request, UserId userId) {
        return new UpdateProjectVisibilityCommand(request.visibility(), organizationId, userId, projectId);
    }

    public static UpdateProjectVisibilityCommand toUpdateProjectVisibilityCommandFromRequestAndStrings(String organizationId, String projectId, UpdateProjectVisibilityRequest request, UserId userId) {
        return new UpdateProjectVisibilityCommand(request.visibility(), OrganizationId.fromString(organizationId), userId, ProjectId.fromString(projectId));
    }
}
