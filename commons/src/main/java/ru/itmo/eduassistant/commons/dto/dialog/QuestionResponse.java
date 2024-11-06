package ru.itmo.eduassistant.commons.dto.dialog;

public record QuestionResponse(
        Long dialogId,
        String authorName,
        String text
) {
}