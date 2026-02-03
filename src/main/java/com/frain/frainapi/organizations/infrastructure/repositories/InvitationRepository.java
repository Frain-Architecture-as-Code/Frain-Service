package com.frain.frainapi.organizations.infrastructure.repositories;

import com.frain.frainapi.organizations.domain.model.Invitation;
import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;
import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationStatus;
import com.frain.frainapi.organizations.domain.model.valueobjects.OrganizationId;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, InvitationId> {
    boolean existsByOrganizationIdAndTargetEmailAndStatus(OrganizationId organizationId, EmailAddress targetEmail, InvitationStatus status);
}
