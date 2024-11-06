package ru.itmo.eduassistant.commons.dto.telegram;

public record SendMessageRequest(
        Long userId,
        String chatId,
        String text
) {
}