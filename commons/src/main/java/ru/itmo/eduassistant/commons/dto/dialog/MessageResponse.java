package ru.itmo.eduassistant.commons.dto.dialog;

import java.time.LocalDateTime;

public record MessageResponse(
        String authorName,
        String text,
        LocalDateTime dateTime
) {
}
