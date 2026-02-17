package com.frain.frainapi.organizations.domain.services;

import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.commands.EnrollMemberCommand;
import com.frain.frainapi.organizations.domain.model.commands.UpdateMemberCommand;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberId;

public interface MemberCommandService {
    Member handle(UpdateMemberCommand command);
    MemberId handle(EnrollMemberCommand command);
}
