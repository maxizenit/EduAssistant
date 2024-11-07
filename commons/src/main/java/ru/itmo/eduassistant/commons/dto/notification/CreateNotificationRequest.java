package ru.itmo.eduassistant.commons.dto.notification;

public record CreateNotificationRequest(
        NotificationType type,
        long channelId,
        String text
) {
}
