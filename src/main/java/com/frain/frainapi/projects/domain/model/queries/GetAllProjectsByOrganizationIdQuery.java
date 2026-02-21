package com.frain.frainapi.projects.domain.model.queries;

import com.frain.frainapi.projects.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;

public record GetAllProjectsByOrganizationIdQuery(OrganizationId organizationId, UserId userId) {
}
