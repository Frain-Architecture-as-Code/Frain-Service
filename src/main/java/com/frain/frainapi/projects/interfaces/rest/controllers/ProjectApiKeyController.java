package com.frain.frainapi.projects.interfaces.rest.controllers;

import com.frain.frainapi.organizations.interfaces.acl.OrganizationContextFacade;
import com.frain.frainapi.projects.domain.model.queries.GetProjectApiKeysQuery;
import com.frain.frainapi.projects.domain.model.valueobjects.MemberId;
import com.frain.frainapi.projects.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectApiKeyId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;
import com.frain.frainapi.projects.domain.services.ProjectApiKeyCommandService;
import com.frain.frainapi.projects.domain.services.ProjectApiKeyQueryService;
import com.frain.frainapi.projects.infrastructure.repositories.ProjectApiKeyRepository;
import com.frain.frainapi.projects.interfaces.rest.controllers.assemblers.ProjectApiKeyAssembler;
import com.frain.frainapi.projects.interfaces.rest.controllers.requests.CreateApiKeyRequest;
import com.frain.frainapi.projects.interfaces.rest.controllers.responses.ProjectApiKeyCreatedResponse;
import com.frain.frainapi.projects.interfaces.rest.controllers.responses.ProjectApiKeyResponse;
import com.frain.frainapi.shared.infrastructure.security.UserContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/projects/{projectId}/api-keys")
@Tag(name="Project API Keys")
public class ProjectApiKeyController {

    private final ProjectApiKeyCommandService projectApiKeyCommandService;
    private final ProjectApiKeyQueryService projectApiKeyQueryService;
    private final ProjectApiKeyRepository projectApiKeyRepository;
    private final OrganizationContextFacade organizationContextFacade;
    private final UserContext userContext;

    public ProjectApiKeyController(
            ProjectApiKeyCommandService projectApiKeyCommandService,
            ProjectApiKeyQueryService projectApiKeyQueryService,
            ProjectApiKeyRepository projectApiKeyRepository,
            OrganizationContextFacade organizationContextFacade,
            UserContext userContext) {
        this.projectApiKeyCommandService = projectApiKeyCommandService;
        this.projectApiKeyQueryService = projectApiKeyQueryService;
        this.projectApiKeyRepository = projectApiKeyRepository;
        this.organizationContextFacade = organizationContextFacade;
        this.userContext = userContext;
    }

    @PostMapping
    public ResponseEntity<ProjectApiKeyCreatedResponse> createApiKey(
            @PathVariable OrganizationId organizationId,
            @PathVariable ProjectId projectId,
            @Valid @RequestBody CreateApiKeyRequest request
    ) {
        var currentUserId = userContext.getCurrentUserId();
        var requestingMemberId = MemberId.fromString(
                organizationContextFacade.getMemberIdForUserInOrganization(
                        organizationId.toString(),
                        currentUserId.toString()
                )
        );

        var command = ProjectApiKeyAssembler.toCreateProjectApiKeyCommand(
                request,
                projectId,
                organizationId,
                requestingMemberId
        );

        var apiKeyId = projectApiKeyCommandService.handle(command);

        // Fetch the created API key to return the full response with the key value
        var createdApiKey = projectApiKeyRepository.findById(apiKeyId)
                .orElseThrow(() -> new RuntimeException("API key not found after creation"));

        return ResponseEntity.ok(ProjectApiKeyAssembler.toCreatedResponse(createdApiKey));
    }

    @GetMapping
    public ResponseEntity<List<ProjectApiKeyResponse>> listApiKeys(
            @PathVariable OrganizationId organizationId,
            @PathVariable ProjectId projectId
    ) {
        var query = new GetProjectApiKeysQuery(projectId);
        var apiKeys = projectApiKeyQueryService.handle(query);

        var response = apiKeys.stream()
                .map(ProjectApiKeyAssembler::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{apiKeyId}")
    public ResponseEntity<Void> revokeApiKey(
            @PathVariable OrganizationId organizationId,
            @PathVariable ProjectId projectId,
            @PathVariable ProjectApiKeyId apiKeyId
    ) {
        var currentUserId = userContext.getCurrentUserId();
        var requestingMemberId = MemberId.fromString(
                organizationContextFacade.getMemberIdForUserInOrganization(
                        organizationId.toString(),
                        currentUserId.toString()
                )
        );

        var command = ProjectApiKeyAssembler.toRevokeCommand(
                apiKeyId,
                organizationId,
                requestingMemberId
        );

        projectApiKeyCommandService.handle(command);

        return ResponseEntity.noContent().build();
    }
}
