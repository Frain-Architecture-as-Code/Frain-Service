package com.frain.frainapi.projects.domain.model.queries;

import com.frain.frainapi.projects.domain.model.valueobjects.MemberId;
import com.frain.frainapi.projects.domain.model.valueobjects.OrganizationId;

public record GetAllProjectsByOrganizationIdQuery(OrganizationId organizationId) {
}
