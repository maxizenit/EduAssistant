package ru.itmo.eduassistant.commons.dto.channel;

public record QuestionRequest(
        Long studentId,
        String text
) {
}
