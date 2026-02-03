package com.frain.frainapi.organizations.domain.services;

import com.frain.frainapi.organizations.domain.model.commands.AcceptInvitationCommand;
import com.frain.frainapi.organizations.domain.model.commands.DeclineInvitationCommand;
import com.frain.frainapi.organizations.domain.model.commands.SendInvitationCommand;

public interface InvitationCommandService {
    void handle(SendInvitationCommand command);
    void handle(DeclineInvitationCommand command);
    void handle(AcceptInvitationCommand command);
}
