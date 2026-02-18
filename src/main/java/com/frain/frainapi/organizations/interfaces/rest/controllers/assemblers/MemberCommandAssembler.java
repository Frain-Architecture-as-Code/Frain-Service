package com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.commands.KickMemberFromOrganizationCommand;
import com.frain.frainapi.organizations.domain.model.commands.UpdateMemberCommand;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberId;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.organizations.interfaces.rest.controllers.requests.UpdateMemberRequest;

public class MemberCommandAssembler {

    public static UpdateMemberCommand toUpdateMemberCommandFromRequest(
            UpdateMemberRequest request,
            String memberId,
            Member currentMember
    ) {
        return new UpdateMemberCommand(MemberId.fromString(memberId), request.newRole(), request.newName(), currentMember);
    }

    public static KickMemberFromOrganizationCommand toKickMemberFromOrganizationCommand(
            String organizationId,
            String memberToKickId,
            Member performBy
    ) {
        return new KickMemberFromOrganizationCommand(
                OrganizationId.fromString(organizationId),
                MemberId.fromString(memberToKickId),
                performBy
        );
    }
}
