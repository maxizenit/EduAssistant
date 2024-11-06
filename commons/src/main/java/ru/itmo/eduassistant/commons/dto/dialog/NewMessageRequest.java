package ru.itmo.eduassistant.commons.dto.dialog;

public record NewMessageRequest(
        long userId,
        String text,
        long dialogId
) {
}
