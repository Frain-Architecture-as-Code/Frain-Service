package com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;

public class OrganizationQueryAssembler {
    public static GetOrganizationByIdQuery toGetOrganizationByIdQuery(String organizationId) {
        return new GetOrganizationByIdQuery(OrganizationId.fromString(organizationId));
    }
}
