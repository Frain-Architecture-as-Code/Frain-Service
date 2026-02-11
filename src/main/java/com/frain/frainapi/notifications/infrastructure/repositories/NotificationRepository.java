package com.frain.frainapi.notifications.infrastructure.repositories;

import com.frain.frainapi.notifications.domain.model.Notification;
import com.frain.frainapi.notifications.domain.model.valueobjects.NotificationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, NotificationId> {

}
