package com.frain.frainapi.organizations.interfaces.rest.controllers.responses;

public record InvitationResponse(String invitationId, String targetEmail, String status, String role, String organizationId, String inviterId, String createdAt, String updatedAt) {
}
