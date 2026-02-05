package com.frain.frainapi.organizations.interfaces.rest.controllers.responses;

public record MemberResponse(String memberId, String userId, String organizationId, String memberName, String memberRole) {
}
