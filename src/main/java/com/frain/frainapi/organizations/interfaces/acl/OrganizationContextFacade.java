package com.frain.frainapi.organizations.interfaces.acl;

public interface OrganizationContextFacade {
    boolean isMemberOfOrganization(String organizationId, String userId);

    String getMemberIdForUserInOrganization(String organizationId, String userId);

    boolean canCreateProjects(String organizationId, String userId);

    boolean canUpdateProject(String organizationId, String userId);

    /**
     * Gets the role of a member in an organization.
     * @param organizationId The organization ID
     * @param memberId The member ID
     * @return The role as a string (OWNER, ADMIN, CONTRIBUTOR)
     */
    String getMemberRole(String organizationId, String memberId);

    /**
     * Gets the organization ID for a given project.
     * @param projectId The project ID
     * @return The organization ID as a string
     */
    String getOrganizationIdByProjectId(String projectId);
}
