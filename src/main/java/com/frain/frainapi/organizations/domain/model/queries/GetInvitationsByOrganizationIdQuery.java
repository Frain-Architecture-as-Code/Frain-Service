package com.frain.frainapi.organizations.domain.model.queries;

import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;

public record GetInvitationsByOrganizationIdQuery(OrganizationId organizationId) {
}
