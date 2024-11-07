package ru.itmo.eduassistant.commons.dto.notification;

import java.time.LocalDateTime;


public record NotificationResponse(
        long id,
        String text,
        String channelName,
        String authorName,

        LocalDateTime dateTime
) {
}

