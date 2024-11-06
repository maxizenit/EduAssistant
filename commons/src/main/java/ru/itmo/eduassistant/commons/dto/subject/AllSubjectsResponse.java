package ru.itmo.eduassistant.commons.dto.subject;

import java.util.List;

public record AllSubjectsResponse(
        List<SubjectResponse> subjects
) {
}
