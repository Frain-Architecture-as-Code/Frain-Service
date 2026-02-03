package com.frain.frainapi.organizations.domain.services;

import com.frain.frainapi.organizations.domain.model.commands.ChangeMemberRoleCommand;
import com.frain.frainapi.organizations.domain.model.commands.UpdateMemberCommand;

public interface MemberCommandService {
    void handle(ChangeMemberRoleCommand command);
    void handle(UpdateMemberCommand command);
}
