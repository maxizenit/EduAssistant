package ru.itmo.eduassistant.commons.dto.dialog;

public record NewQuestionRequest(
        Long studentId,
        Long channelId,
        String text
) {
}
