package ru.itmo.eduassistant.commons.dto.channel;

import java.util.List;

public record AllStudentsInChannelResponse(
        List<StudentInChannelResponse> students
) {
}
