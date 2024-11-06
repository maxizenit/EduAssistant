package ru.itmo.eduassistant.commons.dto.queue;

public record QueueResponse(
        long id,
        String name,
        String expirationDate,
        String subjectName
) {
}
