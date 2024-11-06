package ru.itmo.eduassistant.commons.dto.notification;

public record CreateNotificationRequest(
        String type,
        long channelId,
        String text
) {
}
