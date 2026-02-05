package com.frain.frainapi.organizations.domain.services;

import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.commands.UpdateMemberCommand;

public interface MemberCommandService {
    Member handle(UpdateMemberCommand command);
}
