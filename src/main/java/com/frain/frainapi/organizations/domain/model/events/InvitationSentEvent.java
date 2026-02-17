package com.frain.frainapi.organizations.domain.model.events;

import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationName;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;

public record InvitationSentEvent(
    InvitationId invitationId,
    EmailAddress senderEmail,
    EmailAddress recipientEmail,
    OrganizationName organizationName
) {}
