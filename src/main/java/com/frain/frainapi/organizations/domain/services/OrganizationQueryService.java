package com.frain.frainapi.organizations.domain.services;

import com.frain.frainapi.organizations.domain.model.Organization;
import com.frain.frainapi.organizations.domain.model.queries.GetOrganizationByIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetUserOrganizationsQuery;

import java.util.List;
import java.util.Optional;

public interface OrganizationQueryService {
    Optional<Organization> handle(GetOrganizationByIdQuery query);
    List<Organization> handle(GetUserOrganizationsQuery query);
}
