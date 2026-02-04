package com.frain.frainapi.organizations.application.commandservices;

import com.frain.frainapi.organizations.domain.model.commands.AcceptInvitationCommand;
import com.frain.frainapi.organizations.domain.model.commands.DeclineInvitationCommand;
import com.frain.frainapi.organizations.domain.model.commands.SendInvitationCommand;
import com.frain.frainapi.organizations.domain.services.InvitationCommandService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InvitationCommandServiceImpl implements InvitationCommandService {

    @Override
    @Transactional
    public void handle(SendInvitationCommand command) {

    }

    @Override
    @Transactional
    public void handle(DeclineInvitationCommand command) {

    }

    @Override
    @Transactional
    public void handle(AcceptInvitationCommand command) {

    }
}
