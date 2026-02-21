package com.frain.frainapi.organizations.application.queryservices;

import com.frain.frainapi.organizations.domain.model.Invitation;
import com.frain.frainapi.organizations.domain.model.queries.GetInvitationByIdQuery;
import com.frain.frainapi.organizations.domain.model.queries.GetInvitationsByOrganizationIdQuery;
import com.frain.frainapi.organizations.domain.services.InvitationQueryService;
import com.frain.frainapi.organizations.infrastructure.repositories.InvitationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvitationQueryServiceImpl implements InvitationQueryService {

    private final InvitationRepository invitationRepository;

    public InvitationQueryServiceImpl(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }


    @Override
    public List<Invitation> handle(GetInvitationsByOrganizationIdQuery query) {
        return invitationRepository.findAllByOrganizationId(query.organizationId());
    }

    @Override
    public Optional<Invitation> handle(GetInvitationByIdQuery query) {
        return invitationRepository.findById(query.invitationId());
    }
}
