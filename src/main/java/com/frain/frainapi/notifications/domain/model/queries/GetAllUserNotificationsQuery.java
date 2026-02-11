package com.frain.frainapi.notifications.domain.model.queries;

import com.frain.frainapi.shared.domain.model.valueobjects.EmailAddress;

public record GetAllUserNotificationsQuery(EmailAddress userEmail) {
}
