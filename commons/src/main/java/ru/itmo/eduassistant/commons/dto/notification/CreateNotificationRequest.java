package ru.itmo.eduassistant.commons.dto.notification;

public record CreateNotificationRequest(
        long channelId,
        String text
) {
}
