package ru.itmo.eduassistant.commons.dto.channel;

public record CreateChannelRequest (
        Long teacherId,
        String name,
        String description
) {
}
