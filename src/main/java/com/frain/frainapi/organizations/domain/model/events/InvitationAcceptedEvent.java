package com.frain.frainapi.organizations.domain.model.events;

import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberId;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;

public record InvitationAcceptedEvent(InvitationId invitationId, MemberId memberId, EmailAddress targetEmail) {
}
