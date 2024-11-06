package ru.itmo.eduassistant.commons.dto.question;

import java.util.List;

public record AllQuestionsResponse(
        List<String> questions
) {
}
