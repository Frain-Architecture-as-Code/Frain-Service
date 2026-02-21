package com.frain.frainapi.organizations.interfaces.rest.controllers.requests;

import com.frain.frainapi.organizations.domain.model.valueobjects.MemberName;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberRole;

public record UpdateMemberRequest(MemberName newName, MemberRole newRole) {
}
