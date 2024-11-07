package ru.itmo.eduassistant.commons.dto.channel;

public record ChannelResponse(
        long id,
        String name,
        String teacherName,
        String description
) {
}
