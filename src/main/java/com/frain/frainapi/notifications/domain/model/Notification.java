package com.frain.frainapi.notifications.domain.model;

import com.frain.frainapi.notifications.domain.model.valueobjects.*;
import com.frain.frainapi.shared.domain.model.AuditableEntity;
import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Notification extends AuditableEntity<Notification> {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "notification_id"))
    private NotificationId id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "message"))
    private NotificationMessage message;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "resource_id"))
    private ResourceId resourceId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "recipient_email"))
    private EmailAddress recipientEmail;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "sender_email"))
    private EmailAddress senderEmail;

    public Notification(NotificationId id, NotificationType type, NotificationMessage message, ResourceId resourceId, EmailAddress recipientEmail, EmailAddress senderEmail) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.senderEmail = senderEmail;
        this.status = NotificationStatus.UNREAD;
        this.resourceId = resourceId;
        this.recipientEmail = recipientEmail;
    }

    public void markAsRead() {
        if (this.status == NotificationStatus.UNREAD) {
            this.status = NotificationStatus.READ;
        }
    }

    public void markAsUnread() {
        if (this.status == NotificationStatus.READ) {
            this.status = NotificationStatus.UNREAD;
        }
    }

    public void markAsArchived() {
        if (this.status != NotificationStatus.ARCHIVED) {
            this.status = NotificationStatus.ARCHIVED;
        }
    }

    public void resend() {
        this.status = NotificationStatus.UNREAD;
    }
}
