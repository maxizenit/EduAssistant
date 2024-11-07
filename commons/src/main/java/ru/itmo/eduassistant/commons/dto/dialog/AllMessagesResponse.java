package ru.itmo.eduassistant.commons.dto.dialog;

import java.util.List;

public record AllMessagesResponse(
        List<MessageResponse> messages
) {
}
