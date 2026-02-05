package com.frain.frainapi.organizations.domain.services;

import com.frain.frainapi.organizations.domain.model.commands.AcceptInvitationCommand;
import com.frain.frainapi.organizations.domain.model.commands.DeclineInvitationCommand;
import com.frain.frainapi.organizations.domain.model.commands.SendInvitationCommand;
import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;

public interface InvitationCommandService {
    InvitationId handle(SendInvitationCommand command);
    InvitationId handle(DeclineInvitationCommand command);
    InvitationId handle(AcceptInvitationCommand command);
}
