package com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.commands.UpdateMemberCommand;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberId;
import com.frain.frainapi.organizations.interfaces.rest.controllers.requests.UpdateMemberRequest;

public class MemberCommandAssembler {

    public static UpdateMemberCommand toUpdateMemberCommandFromRequest(
            UpdateMemberRequest request,
            MemberId memberId,
            Member currentMember
    ) {
        return new UpdateMemberCommand(memberId, request.newRole(), request.newName(), currentMember);
    }
}
