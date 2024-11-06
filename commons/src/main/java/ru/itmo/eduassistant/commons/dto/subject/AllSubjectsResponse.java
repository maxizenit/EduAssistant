package ru.itmo.eduassistant.commons.dto.subject;

import javax.security.auth.Subject;
import java.util.List;

public record AllSubjectsResponse(
    List<Subject> subjects
) {
}
