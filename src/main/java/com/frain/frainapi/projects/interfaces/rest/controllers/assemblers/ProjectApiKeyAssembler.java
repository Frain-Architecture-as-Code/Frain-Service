package com.frain.frainapi.projects.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.projects.domain.model.ProjectApiKey;
import com.frain.frainapi.projects.domain.model.commands.CreateProjectApiKeyCommand;
import com.frain.frainapi.projects.domain.model.commands.RevokeProjectApiKeyCommand;
import com.frain.frainapi.projects.domain.model.valueobjects.MemberId;
import com.frain.frainapi.projects.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectApiKeyId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;
import com.frain.frainapi.projects.interfaces.rest.controllers.requests.CreateApiKeyRequest;
import com.frain.frainapi.projects.interfaces.rest.controllers.responses.ProjectApiKeyCreatedResponse;
import com.frain.frainapi.projects.interfaces.rest.controllers.responses.ProjectApiKeyResponse;

public class ProjectApiKeyAssembler {

    public static CreateProjectApiKeyCommand toCreateProjectApiKeyCommand(
            CreateApiKeyRequest request,
            ProjectId projectId,
            OrganizationId organizationId,
            MemberId requestingMemberId
    ) {
        return new CreateProjectApiKeyCommand(
                projectId,
                organizationId,
                MemberId.fromString(request.targetMemberId()),
                requestingMemberId
        );
    }

    public static CreateProjectApiKeyCommand toCreateProjectApiKeyCommandFromStrings(
            CreateApiKeyRequest request,
            String projectId,
            String organizationId,
            MemberId requestingMemberId
    ) {
        return new CreateProjectApiKeyCommand(
                ProjectId.fromString(projectId),
                OrganizationId.fromString(organizationId),
                MemberId.fromString(request.targetMemberId()),
                requestingMemberId
        );
    }

    public static RevokeProjectApiKeyCommand toRevokeCommand(
            ProjectApiKeyId apiKeyId,
            OrganizationId organizationId,
            MemberId requestingMemberId
    ) {
        return new RevokeProjectApiKeyCommand(
                apiKeyId,
                organizationId,
                requestingMemberId
        );
    }

    public static RevokeProjectApiKeyCommand toRevokeCommandFromStrings(
            String apiKeyId,
            String organizationId,
            MemberId requestingMemberId
    ) {
        return new RevokeProjectApiKeyCommand(
                ProjectApiKeyId.fromString(apiKeyId),
                OrganizationId.fromString(organizationId),
                requestingMemberId
        );
    }

    public static ProjectApiKeyCreatedResponse toCreatedResponse(ProjectApiKey apiKey) {
        return new ProjectApiKeyCreatedResponse(
                apiKey.getId().toString(),
                apiKey.getProjectId().toString(),
                apiKey.getMemberId().toString(),
                apiKey.getApiKey().value(),
                apiKey.getCreatedAt() != null ? apiKey.getCreatedAt().toString() : null
        );
    }

    public static ProjectApiKeyResponse toResponse(ProjectApiKey apiKey) {
        // Only show first 12 characters of the API key for security
        String prefix = apiKey.getApiKey().value();
        if (prefix.length() > 12) {
            prefix = prefix.substring(0, 12) + "...";
        }

        return new ProjectApiKeyResponse(
                apiKey.getId().toString(),
                apiKey.getProjectId().toString(),
                apiKey.getMemberId().toString(),
                prefix,
                apiKey.getLastUsedAt() != null ? apiKey.getLastUsedAt().toString() : null,
                apiKey.getCreatedAt() != null ? apiKey.getCreatedAt().toString() : null
        );
    }
}
