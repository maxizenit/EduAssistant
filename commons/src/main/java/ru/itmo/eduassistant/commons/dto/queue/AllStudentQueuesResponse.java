package ru.itmo.eduassistant.commons.dto.queue;

import java.util.List;

public record AllStudentQueuesResponse(
        List<QueueResponse> queues
) {
}
