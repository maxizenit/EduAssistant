package ru.itmo.eduassistant.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itmo.eduassistant.backend.entity.Queue;
import ru.itmo.eduassistant.commons.dto.queue.QueueResponse;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QueueMapper {

    @Mapping(source = "channel.name", target = "channelName")
    QueueResponse toQueueResponse(Queue queue);

    List<QueueResponse> toQueueResponseList(List<Queue> queues);
}
