package com.frain.frainapi.organizations.domain.services;

import com.frain.frainapi.organizations.domain.model.commands.CreateOrganizationCommand;
import com.frain.frainapi.organizations.domain.model.commands.DeleteOrganizationCommand;
import com.frain.frainapi.organizations.domain.model.commands.UpdateOrganizationCommand;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;

public interface OrganizationCommandService {
    void handle(UpdateOrganizationCommand command);
    OrganizationId handle(CreateOrganizationCommand command);
    OrganizationId handle(DeleteOrganizationCommand command);

}
