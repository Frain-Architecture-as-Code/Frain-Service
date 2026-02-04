package com.frain.frainapi.organizations.domain.services;

import com.frain.frainapi.organizations.domain.model.commands.UpdateMemberCommand;

public interface MemberCommandService {
    void handle(UpdateMemberCommand command);
}
