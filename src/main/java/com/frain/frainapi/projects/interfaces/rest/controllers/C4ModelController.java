package com.frain.frainapi.projects.interfaces.rest.controllers;

import com.frain.frainapi.projects.domain.exceptions.ProjectNotFoundException;
import com.frain.frainapi.projects.domain.exceptions.ViewNotFoundException;
import com.frain.frainapi.projects.domain.model.Project;
import com.frain.frainapi.projects.domain.model.commands.UpdateC4ModelCommand;
import com.frain.frainapi.projects.domain.model.commands.UpdateNodePositionCommand;
import com.frain.frainapi.projects.domain.model.queries.GetProjectByIdQuery;
import com.frain.frainapi.projects.domain.model.valueobjects.C4Model;
import com.frain.frainapi.projects.domain.model.valueobjects.FrainViewJSON;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;
import com.frain.frainapi.projects.domain.services.ProjectCommandService;
import com.frain.frainapi.projects.domain.services.ProjectQueryService;
import com.frain.frainapi.projects.interfaces.rest.controllers.requests.UpdateNodePositionRequest;
import com.frain.frainapi.projects.interfaces.rest.controllers.responses.C4ModelResponse;
import com.frain.frainapi.projects.interfaces.rest.controllers.responses.ViewDetailResponse;
import com.frain.frainapi.projects.interfaces.rest.controllers.responses.ViewSummaryResponse;
import com.frain.frainapi.shared.infrastructure.security.ApiKeyAuthenticationToken;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/c4models/projects/{projectId}")
@Tag(name = "C4 Model")
public class C4ModelController {

    private final ProjectCommandService projectCommandService;
    private final ProjectQueryService projectQueryService;

    public C4ModelController(
            ProjectCommandService projectCommandService,
            ProjectQueryService projectQueryService) {
        this.projectCommandService = projectCommandService;
        this.projectQueryService = projectQueryService;
    }

    // SDK endpoint
    @PutMapping
    public ResponseEntity<C4ModelResponse> updateC4Model(
            @PathVariable ProjectId projectId,
            @Valid @RequestBody C4Model c4Model
    ) {
        validateApiKeyForProject(projectId);

        var command = new UpdateC4ModelCommand(projectId, c4Model);
        projectCommandService.handle(command);

        var project = getProjectOrThrow(projectId);

        return ResponseEntity.ok(new C4ModelResponse(
                projectId.toString(),
                project.getC4Model()
        ));
    }

    @GetMapping
    public ResponseEntity<C4ModelResponse> getC4Model(@PathVariable ProjectId projectId) {
        validateApiKeyForProject(projectId);

        var project = getProjectOrThrow(projectId);

        return ResponseEntity.ok(new C4ModelResponse(
                projectId.toString(),
                project.getC4Model()
        ));
    }

    @GetMapping("/views")
    public ResponseEntity<List<ViewSummaryResponse>> getViewSummaries(@PathVariable ProjectId projectId) {
        validateApiKeyForProject(projectId);

        var project = getProjectOrThrow(projectId);

        var summaries = project.getViewSummaries().stream()
                .map(vs -> new ViewSummaryResponse(
                        vs.id(),
                        vs.type(),
                        vs.name(),
                        vs.container()
                ))
                .toList();

        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/views/{viewId}")
    public ResponseEntity<ViewDetailResponse> getViewDetail(
            @PathVariable ProjectId projectId,
            @PathVariable String viewId
    ) {
        validateApiKeyForProject(projectId);

        var project = getProjectOrThrow(projectId);
        var view = project.getViewById(viewId)
                .orElseThrow(() -> new ViewNotFoundException(viewId, projectId.toString()));

        return ResponseEntity.ok(toViewDetailResponse(view));
    }

    @PatchMapping("/views/{viewId}/nodes/{nodeId}")
    public ResponseEntity<ViewDetailResponse> updateNodePosition(
            @PathVariable ProjectId projectId,
            @PathVariable String viewId,
            @PathVariable String nodeId,
            @Valid @RequestBody UpdateNodePositionRequest request
    ) {
        validateApiKeyForProject(projectId);

        var command = new UpdateNodePositionCommand(
                projectId,
                viewId,
                nodeId,
                request.x(),
                request.y()
        );

        projectCommandService.handle(command);

        // Return the updated view
        var project = getProjectOrThrow(projectId);
        var view = project.getViewById(viewId)
                .orElseThrow(() -> new ViewNotFoundException(viewId, projectId.toString()));

        return ResponseEntity.ok(toViewDetailResponse(view));
    }

    private Project getProjectOrThrow(ProjectId projectId) {
        return projectQueryService.handle(new GetProjectByIdQuery(projectId))
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
    }

    private void validateApiKeyForProject(ProjectId projectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (!(authentication instanceof ApiKeyAuthenticationToken apiKeyAuth)) {
            throw new SecurityException("API Key authentication required");
        }

        if (!apiKeyAuth.getProjectId().equals(projectId)) {
            throw new SecurityException("API Key not authorized for this project");
        }
    }

    private ViewDetailResponse toViewDetailResponse(FrainViewJSON view) {
        return new ViewDetailResponse(
                view.id(),
                view.type(),
                view.name(),
                view.container(),
                view.nodes(),
                view.externalNodes(),
                view.relations()
        );
    }
}
