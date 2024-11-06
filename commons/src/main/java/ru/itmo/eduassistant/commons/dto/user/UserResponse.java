package ru.itmo.eduassistant.commons.dto.user;

public record UserResponse(
        long id,
        long telegramId,
        UserRole role
) {
}
