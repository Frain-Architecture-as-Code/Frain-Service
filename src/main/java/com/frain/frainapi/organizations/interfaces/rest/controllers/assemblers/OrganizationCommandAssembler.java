package com.frain.frainapi.organizations.interfaces.rest.controllers.assemblers;

import com.frain.frainapi.organizations.domain.model.Member;
import com.frain.frainapi.organizations.domain.model.commands.CreateOrganizationCommand;
import com.frain.frainapi.organizations.domain.model.commands.UpdateOrganizationCommand;
import com.frain.frainapi.organizations.domain.model.valueobjects.MemberName;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.organizations.interfaces.rest.controllers.requests.CreateOrganizationRequest;
import com.frain.frainapi.organizations.interfaces.rest.controllers.requests.UpdateOrganizationRequest;
import com.frain.frainapi.shared.domain.model.valueobjects.User;

public class OrganizationCommandAssembler {
    public static CreateOrganizationCommand toCreateOrganizationCommandFromRequest(CreateOrganizationRequest request, User currentUser) {
       return new CreateOrganizationCommand(request.name(), request.visibility(), currentUser.id(), new MemberName(currentUser.userName().toString()));
    }

    public static UpdateOrganizationCommand toUpdateOrganizationCommandFromRequest(OrganizationId organizationId, UpdateOrganizationRequest request, Member requestingMember) {
        return new UpdateOrganizationCommand(organizationId, request.name(), request.visibility(), requestingMember);
    }
}
