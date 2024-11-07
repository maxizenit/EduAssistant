package ru.itmo.eduassistant.commons.dto.telegram;

import ru.itmo.eduassistant.commons.dto.notification.NotificationResponse;

import java.util.List;

public record SendNotificationRequest(
        List<Long> recipientIds,
        NotificationResponse notification
) {
}