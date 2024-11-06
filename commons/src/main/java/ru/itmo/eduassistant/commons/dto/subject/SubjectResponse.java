package ru.itmo.eduassistant.commons.dto.subject;

public record SubjectResponse(
        long id,
        String name,
        String teacherName
) {
}
