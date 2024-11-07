package ru.itmo.eduassistant.commons.dto.queue;

import java.util.List;

public record AllStudentInQueueResponse(
        List<User> allStudents
) {
    public record User(
            long telegramId,
            String fio
    ) {

    }
}
