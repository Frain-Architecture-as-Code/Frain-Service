package com.frain.frainapi.projects.interfaces.rest.controllers;

import com.frain.frainapi.organizations.interfaces.acl.OrganizationContextFacade;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Component;

@Component
public class ProjectContextUtils {
    private final OrganizationContextFacade organizationContextFacade;

    public ProjectContextUtils(OrganizationContextFacade organizationContextFacade) {
        this.organizationContextFacade = organizationContextFacade;
    }

    public boolean canUserCreateProjects(UserId userId, String organizationId) {
        return organizationContextFacade.canCreateProjects(organizationId, userId.toString());
    }

    public boolean isUserPartOfOrganization(UserId userId, String organizationId) {
        return organizationContextFacade.isMemberOfOrganization(organizationId, userId.toString());
    }
}
