package ru.itmo.eduassistant.commons.dto.question;

public record NewMessageRequest(
        long userId,
        String text,
        long dialogId
) {
}
