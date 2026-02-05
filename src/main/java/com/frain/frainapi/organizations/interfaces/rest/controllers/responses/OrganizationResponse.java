package com.frain.frainapi.organizations.interfaces.rest.controllers.responses;

public record OrganizationResponse(String organizationId, String ownerMemberId, String name, String visibility) {
}
