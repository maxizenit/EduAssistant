package ru.itmo.eduassistant.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itmo.eduassistant.backend.entity.Notification;
import ru.itmo.eduassistant.commons.dto.notification.NotificationResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "body", target = "text")
    @Mapping(source = "datetime", target = "dateTime")
    NotificationResponse toResponse(Notification notification);
}
