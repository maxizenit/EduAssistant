package ru.itmo.eduassistant.commons.dto.notofication;

import java.time.LocalDateTime;

public record AllNotificationsResponse(
        long id,
        String text,
        LocalDateTime timestamp,
        String subject
) {
}
