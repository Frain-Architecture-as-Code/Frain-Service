package com.frain.frainapi.organizations.domain.model.queries;

import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;

public record GetMemberByUserIdAndOrganizationIdQuery(UserId userId, OrganizationId organizationId) {
}
