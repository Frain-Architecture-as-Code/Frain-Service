package com.frain.frainapi.organizations.interfaces.acl;

public interface OrganizationContextFacade {
    boolean isMemberOfOrganization(String organizationId, String userId);

    String getMemberIdForUserInOrganization(String organizationId, String userId);

    boolean canCreateProjects(String organizationId, String userId);

    boolean canUpdateProject(String organizationId, String userId);
}
