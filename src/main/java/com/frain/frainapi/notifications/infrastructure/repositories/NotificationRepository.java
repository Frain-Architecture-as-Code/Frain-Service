package com.frain.frainapi.notifications.infrastructure.repositories;

import com.frain.frainapi.organizations.domain.model.Invitation;
import com.frain.frainapi.organizations.domain.model.valueobjects.InvitationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Invitation, InvitationId> {

}
