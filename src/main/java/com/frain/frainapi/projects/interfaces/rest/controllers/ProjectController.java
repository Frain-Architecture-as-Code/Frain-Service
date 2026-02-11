package com.frain.frainapi.projects.interfaces.rest.controllers;

import com.frain.frainapi.projects.domain.exceptions.ProjectNotFoundException;
import com.frain.frainapi.projects.domain.model.queries.GetProjectByIdQuery;
import com.frain.frainapi.projects.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;
import com.frain.frainapi.projects.domain.services.ProjectCommandService;
import com.frain.frainapi.projects.domain.services.ProjectQueryService;
import com.frain.frainapi.projects.interfaces.rest.controllers.assemblers.ProjectAssembler;
import com.frain.frainapi.projects.interfaces.rest.controllers.assemblers.ProjectCommandAssembler;
import com.frain.frainapi.projects.interfaces.rest.controllers.requests.CreateProjectRequest;
import com.frain.frainapi.projects.interfaces.rest.controllers.requests.UpdateProjectVisibilityRequest;
import com.frain.frainapi.projects.interfaces.rest.controllers.responses.ProjectResponse;
import com.frain.frainapi.shared.infrastructure.security.UserContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/projects")
public class ProjectController {
    private final ProjectCommandService projectCommandService;
    private final ProjectQueryService projectQueryService;
    private final UserContext userContext;

    public ProjectController(ProjectCommandService projectCommandService, ProjectQueryService projectQueryService, UserContext userContext) {
        this.projectCommandService = projectCommandService;
        this.projectQueryService = projectQueryService;
        this.userContext = userContext;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@PathVariable OrganizationId organizationId, @RequestBody CreateProjectRequest request) {
        var currentUserId = userContext.getCurrentUserId();

        var command = ProjectCommandAssembler.toCreateProjectCommandFromRequest(request, currentUserId, organizationId);

        var result = projectCommandService.handle(command);

        var projectResult = projectQueryService.handle(new GetProjectByIdQuery(result));

        if (projectResult.isEmpty()) {
            throw new ProjectNotFoundException(result);
        }

        var response = ProjectAssembler.toResponseFromEntity(projectResult.get());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable OrganizationId organizationId, @PathVariable ProjectId projectId, @RequestBody UpdateProjectVisibilityRequest request) {
        var userId = userContext.getCurrentUserId();
        var command = ProjectCommandAssembler.toUpdateProjectVisibilityCommandFromRequest(organizationId, projectId, request, userId);

        var result = projectCommandService.handle(command);

        var projectResult = projectQueryService.handle(new GetProjectByIdQuery(result));

        if (projectResult.isEmpty()) {
            throw new ProjectNotFoundException(result);
        }

        var response = ProjectAssembler.toResponseFromEntity(projectResult.get());

        return ResponseEntity.ok(response);
    }
}
