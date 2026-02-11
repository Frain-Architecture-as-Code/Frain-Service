package com.frain.frainapi.projects.application.commandservices;

import com.frain.frainapi.organizations.interfaces.acl.OrganizationContextFacade;
import com.frain.frainapi.projects.domain.exceptions.ApiKeyAlreadyExistsException;
import com.frain.frainapi.projects.domain.exceptions.ApiKeyNotFoundException;
import com.frain.frainapi.projects.domain.exceptions.CannotManageApiKeyException;
import com.frain.frainapi.projects.domain.model.ProjectApiKey;
import com.frain.frainapi.projects.domain.model.commands.CreateProjectApiKeyCommand;
import com.frain.frainapi.projects.domain.model.commands.RecordApiKeyUsageCommand;
import com.frain.frainapi.projects.domain.model.commands.RevokeProjectApiKeyCommand;
import com.frain.frainapi.projects.domain.model.valueobjects.ApiKey;
import com.frain.frainapi.projects.domain.model.valueobjects.MemberRole;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectApiKeyId;
import com.frain.frainapi.projects.domain.services.ProjectApiKeyCommandService;
import com.frain.frainapi.projects.infrastructure.repositories.ProjectApiKeyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ProjectApiKeyCommandServiceImpl implements ProjectApiKeyCommandService {

    private final ProjectApiKeyRepository projectApiKeyRepository;
    private final OrganizationContextFacade organizationContextFacade;

    public ProjectApiKeyCommandServiceImpl(
            ProjectApiKeyRepository projectApiKeyRepository,
            OrganizationContextFacade organizationContextFacade) {
        this.projectApiKeyRepository = projectApiKeyRepository;
        this.organizationContextFacade = organizationContextFacade;
    }

    @Override
    @Transactional
    public ProjectApiKeyId handle(CreateProjectApiKeyCommand command) {
        // Get roles from organization context
        String requestingRoleStr = organizationContextFacade.getMemberRole(
                command.organizationId().toString(),
                command.requestingMemberId().toString()
        );
        String targetRoleStr = organizationContextFacade.getMemberRole(
                command.organizationId().toString(),
                command.targetMemberId().toString()
        );

        MemberRole requestingRole = MemberRole.fromString(requestingRoleStr);
        MemberRole targetRole = MemberRole.fromString(targetRoleStr);

        // Validate permissions
        validateCanCreateApiKey(requestingRole, targetRole);

        // Check if member already has an API key for this project
        boolean exists = projectApiKeyRepository.existsByProjectIdAndMemberId(
                command.projectId(),
                command.targetMemberId()
        );

        if (exists) {
            throw new ApiKeyAlreadyExistsException(command.projectId(), command.targetMemberId());
        }

        // Generate new API key
        ApiKey apiKey = ApiKey.generate();
        ProjectApiKeyId apiKeyId = ProjectApiKeyId.generate();

        ProjectApiKey projectApiKey = new ProjectApiKey(
                apiKeyId,
                command.projectId(),
                command.targetMemberId(),
                apiKey
        );

        projectApiKeyRepository.save(projectApiKey);

        log.info("Created API key {} for member {} in project {}",
                apiKeyId, command.targetMemberId(), command.projectId());

        return apiKeyId;
    }

    @Override
    @Transactional
    public void handle(RevokeProjectApiKeyCommand command) {
        // Find the API key
        ProjectApiKey apiKey = projectApiKeyRepository.findById(command.apiKeyId())
                .orElseThrow(() -> new ApiKeyNotFoundException(command.apiKeyId()));

        // Get roles
        String requestingRoleStr = organizationContextFacade.getMemberRole(
                command.organizationId().toString(),
                command.requestingMemberId().toString()
        );
        String targetRoleStr = organizationContextFacade.getMemberRole(
                command.organizationId().toString(),
                apiKey.getMemberId().toString()
        );

        MemberRole requestingRole = MemberRole.fromString(requestingRoleStr);
        MemberRole targetRole = MemberRole.fromString(targetRoleStr);

        // Validate permissions
        validateCanRevokeApiKey(requestingRole, targetRole);

        projectApiKeyRepository.delete(apiKey);

        log.info("Revoked API key {} for member {} in project {}",
                command.apiKeyId(), apiKey.getMemberId(), apiKey.getProjectId());
    }

    /**
     * Validates if the requesting member can create an API key for the target member.
     * Rules:
     * - Owner can create for anyone
     * - Admin can create for Contributors only (not for Admins or Owner)
     * - Contributor cannot create API keys
     */
    private void validateCanCreateApiKey(MemberRole requestingRole, MemberRole targetRole) {
        if (requestingRole.isContributor()) {
            throw CannotManageApiKeyException.contributorCannotCreate();
        }

        if (requestingRole.isOwner()) {
            // Owner can create for anyone
            return;
        }

        // Admin can only create for Contributors
        if (requestingRole.isAdmin()) {
            if (!targetRole.isContributor()) {
                throw CannotManageApiKeyException.cannotManageHigherRole();
            }
        }
    }

    /**
     * Validates if the requesting member can revoke an API key of the target member.
     * Rules:
     * - Owner can revoke anyone's key
     * - Admin can revoke Contributors' keys only
     * - Contributor cannot revoke API keys
     */
    private void validateCanRevokeApiKey(MemberRole requestingRole, MemberRole targetRole) {
        if (requestingRole.isContributor()) {
            throw CannotManageApiKeyException.insufficientRole();
        }

        if (requestingRole.isOwner()) {
            // Owner can revoke anyone's key
            return;
        }

        // Admin can only revoke Contributors' keys
        if (requestingRole.isAdmin()) {
            if (!targetRole.isContributor()) {
                throw CannotManageApiKeyException.cannotManageHigherRole();
            }
        }
    }

    @Override
    @Transactional
    public void handle(RecordApiKeyUsageCommand command) {
        ProjectApiKey apiKey = projectApiKeyRepository.findById(command.apiKeyId())
                .orElseThrow(() -> new ApiKeyNotFoundException(command.apiKeyId()));

        apiKey.recordUsage();
        projectApiKeyRepository.save(apiKey);

        log.debug("Recorded usage for API key {}", command.apiKeyId());
    }
}
