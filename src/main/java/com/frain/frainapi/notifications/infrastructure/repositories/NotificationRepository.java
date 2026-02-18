package com.frain.frainapi.notifications.infrastructure.repositories;

import com.frain.frainapi.notifications.domain.model.Notification;
import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationId;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, NotificationId> {

    List<Notification> findAllByRecipientEmail(EmailAddress recipientEmail);
}
