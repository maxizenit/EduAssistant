package ru.itmo.eduassistant.commons.dto.queue;

public record QueueResponse(
        long id,
        String expirationDate,
        String subjectName
) {
}
