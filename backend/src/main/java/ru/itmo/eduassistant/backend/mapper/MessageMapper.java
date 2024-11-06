package ru.itmo.eduassistant.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itmo.eduassistant.backend.entity.Message;
import ru.itmo.eduassistant.commons.dto.dialog.MessageResponse;
import ru.itmo.eduassistant.commons.dto.dialog.QuestionResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {
    @Mapping(source = "message.dialog.id", target = "dialogId")
    @Mapping(source = "message.author.fio", target = "authorName")
    @Mapping(source = "message.body", target = "text")
    QuestionResponse toQuestionResponse(Message message);

    @Mapping(source = "message.author.fio", target = "authorName")
    @Mapping(source = "message.body", target = "text")
    @Mapping(source = "message.datetime", target = "dateTime")
    MessageResponse toMessageResponse(Message message);
}
