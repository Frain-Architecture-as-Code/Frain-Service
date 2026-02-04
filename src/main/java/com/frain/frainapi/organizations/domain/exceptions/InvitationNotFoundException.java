package com.frain.frainapi.organizations.domain.exceptions;

import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;

public class InvitationNotFoundException extends RuntimeException {
    public InvitationNotFoundException(InvitationId invitationId) {
        super(String.format("Invitation with ID %s not found.", invitationId));
    }
}
