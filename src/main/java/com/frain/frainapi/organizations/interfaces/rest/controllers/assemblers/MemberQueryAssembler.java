package com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.organizations.domain.model.queries.GetMemberByUserIdAndOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;

public class MemberQueryAssembler {
    public static GetMemberByUserIdAndOrganizationIdQuery toGetMemberByUserIdAndOrganizationIdQueryFromString(UserId userId, String organizationId) {
        return new GetMemberByUserIdAndOrganizationIdQuery(userId, OrganizationId.fromString(organizationId));
    }
}
