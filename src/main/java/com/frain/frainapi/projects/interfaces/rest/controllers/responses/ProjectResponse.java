package com.frain.frainapi.projects.interfaces.rest.controllers.responses;

public record ProjectResponse(String projectId, String organizationId, String visibility, String createdAt, String updatedAt) {
}
