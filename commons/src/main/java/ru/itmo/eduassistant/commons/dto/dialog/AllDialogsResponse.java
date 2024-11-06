package ru.itmo.eduassistant.commons.dto.dialog;

import java.util.List;

public record AllDialogsResponse(
        List<QuestionResponse> questionResponses
) {
}
