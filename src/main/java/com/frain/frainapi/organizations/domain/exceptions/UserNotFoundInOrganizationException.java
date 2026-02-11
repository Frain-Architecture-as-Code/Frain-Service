package com.frain.frainapi.organizations.domain.exceptions;

import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.shared.domain.model.valueobjects.UserId;

public class UserNotFoundInOrganizationException extends RuntimeException {
    public UserNotFoundInOrganizationException(UserId userId, OrganizationId organizationId) {
        super(String.format("User with ID %s not found in organization with ID %s", userId.toString(), organizationId.toString()));
    }
}
