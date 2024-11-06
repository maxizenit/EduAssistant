package ru.itmo.eduassistant.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itmo.eduassistant.backend.entity.Subject;
import ru.itmo.eduassistant.commons.dto.subject.SubjectResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubjectMapper {
    @Mapping(target = "teacherName", source = "teacher.fio")
    SubjectResponse toResponse(Subject subject);
}
