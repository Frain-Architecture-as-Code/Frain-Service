package com.frain.frainapi.organizations.interfaces.rest.controllers.requests;

import com.frain.frainapi.organizations.domain.model.valueobjects.MemberRole;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;

public record SendInvitationRequest(EmailAddress targetEmail, MemberRole role) {
}
