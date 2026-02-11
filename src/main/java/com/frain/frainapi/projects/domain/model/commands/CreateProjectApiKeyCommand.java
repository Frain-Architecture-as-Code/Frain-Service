package com.frain.frainapi.projects.domain.model.commands;

import com.frain.frainapi.projects.domain.model.valueobjects.MemberId;
import com.frain.frainapi.projects.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.projects.domain.model.valueobjects.ProjectId;

/**
 * Command to create an API key for a project member.
 * 
 * @param projectId The project for which the API key is being created
 * @param organizationId The organization that owns the project
 * @param targetMemberId The member who will receive the API key
 * @param requestingMemberId The member who is requesting the API key creation
 */
public record CreateProjectApiKeyCommand(
        ProjectId projectId,
        OrganizationId organizationId,
        MemberId targetMemberId,
        MemberId requestingMemberId
) {}
