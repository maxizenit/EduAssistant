package ru.itmo.eduassistant.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.itmo.eduassistant.backend.entity.Queue;
import ru.itmo.eduassistant.commons.dto.queue.AllStudentQueuesResponse;
import ru.itmo.eduassistant.commons.dto.queue.AllTeacherQueuesResponse;
import ru.itmo.eduassistant.commons.dto.queue.QueueResponse;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QueueMapper {

    QueueResponse toQueueResponse(Queue queue);

    AllStudentQueuesResponse toQueueResponseList(List<QueueResponse> queues);

    AllTeacherQueuesResponse toTeacherQueueResponseList(List<QueueResponse> queues);
}
