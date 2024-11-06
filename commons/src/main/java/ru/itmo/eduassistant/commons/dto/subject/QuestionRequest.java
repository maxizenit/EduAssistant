package ru.itmo.eduassistant.commons.dto.subject;

public record QuestionRequest(
        Long studentId,
        String text
) {
}
