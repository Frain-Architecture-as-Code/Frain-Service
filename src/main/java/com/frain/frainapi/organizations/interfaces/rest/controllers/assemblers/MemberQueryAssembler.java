package com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.organizations.domain.model.queries.GetMemberByIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetMemberByUserIdAndOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberId;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;

public class MemberQueryAssembler {
    public static GetMemberByUserIdAndOrganizationIdQuery toGetMemberByUserIdAndOrganizationIdQueryFromString(UserId userId, String organizationId) {
        return new GetMemberByUserIdAndOrganizationIdQuery(userId, OrganizationId.fromString(organizationId));
    }
    public static GetMemberByIdQuery toGetMemberByIdQuery(String memberId) {
        return new GetMemberByIdQuery(MemberId.fromString(memberId));
    }
}
