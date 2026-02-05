package com.frain.frainapi.organizations.domain.services;

import com.frain.frainapi.organizations.domain.model.Invitation;
import com.frain.frainapi.organizations.domain.model.queries.GetInvitationByIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetInvitationsByOrganizationIdQuery;

import java.util.List;
import java.util.Optional;

public interface InvitationQueryService {
    List<Invitation> handle(GetInvitationsByOrganizationIdQuery query);
    Optional<Invitation> handle(GetInvitationByIdQuery query);
}
