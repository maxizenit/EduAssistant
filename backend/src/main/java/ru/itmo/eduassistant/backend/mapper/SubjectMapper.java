package ru.itmo.eduassistant.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.itmo.eduassistant.backend.entity.Subject;
import ru.itmo.eduassistant.commons.dto.subject.SubjectResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubjectMapper {
    SubjectResponse toResponse(Subject subject);
}
