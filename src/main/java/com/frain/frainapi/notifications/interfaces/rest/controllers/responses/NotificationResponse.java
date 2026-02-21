package com.frain.frainapi.notifications.interfaces.rest.controllers.responses;

public record NotificationResponse(String notificationId, String type, String message, String senderEmail, String status, String resourceId, String recipientEmail, String createdAt, String updatedAt) {
}
