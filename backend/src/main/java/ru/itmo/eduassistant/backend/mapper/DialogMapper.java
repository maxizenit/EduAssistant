package ru.itmo.eduassistant.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itmo.eduassistant.backend.entity.Dialog;
import ru.itmo.eduassistant.commons.dto.dialog.QuestionResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DialogMapper {
    @Mapping(source = "dialog.id", target = "dialogId")
    @Mapping(source = "dialog.author.fio", target = "authorName")
    @Mapping(source = "dialog.firstMessage", target = "text")
    QuestionResponse toQuestionResponse(Dialog dialog);
}
