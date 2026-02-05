package com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.organizations.domain.model.Organization;
import com.frain.frainapi.organizations.interfaces.rest.controllers.responses.OrganizationResponse;

import java.util.List;

public class OrganizationAssembler {
    public static OrganizationResponse toResponseFromEntity(Organization entity) {
        return new OrganizationResponse(
                entity.getId().toString(),
                entity.getOwnerMemberId().toString(),
                entity.getName().toString(),
                entity.getVisibility().toString()
        );
    }

    public static List<OrganizationResponse> toResponsesFromEntities(List<Organization> entities) {
        return entities.stream()
                .map(OrganizationAssembler::toResponseFromEntity)
                .toList();
    }
}
