package ru.itmo.eduassistant.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itmo.eduassistant.backend.entity.Channel;
import ru.itmo.eduassistant.commons.dto.channel.ChannelResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChannelMapper {
    @Mapping(target = "teacherName", source = "teacher.fio")
    ChannelResponse toResponse(Channel channel);
}
