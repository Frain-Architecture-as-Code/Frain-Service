package com.frain.frainapi.notifications.interfaces.acl;

public interface NotificationContextAcl {
    String sendNotification(String recipient, String sender, String message, String resourceId, String type);
}
