package com.frain.frainapi.projects.domain.model.commands;

import com.frain.frainapi.projects.domain.model.valueobjects.MemberId;
import com.frain.frainapi.projects.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectApiKeyId;

/**
 * Command to revoke (delete) an API key.
 * 
 * @param apiKeyId The ID of the API key to revoke
 * @param organizationId The organization that owns the project
 * @param requestingMemberId The member who is requesting the revocation
 */
public record RevokeProjectApiKeyCommand(
        ProjectApiKeyId apiKeyId,
        OrganizationId organizationId,
        MemberId requestingMemberId
) {}
