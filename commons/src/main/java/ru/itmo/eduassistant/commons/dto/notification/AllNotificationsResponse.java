package ru.itmo.eduassistant.commons.dto.notification;

import java.util.List;

public record AllNotificationsResponse(
        String subject,
        List<NotificationResponse> notifications
) {
}
