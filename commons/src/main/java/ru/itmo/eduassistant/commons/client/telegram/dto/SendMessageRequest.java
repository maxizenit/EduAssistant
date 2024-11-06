package ru.itmo.eduassistant.commons.client.telegram.dto;

public record SendMessageRequest(
        Long userId,
        String chatId,
        String text
) {
}