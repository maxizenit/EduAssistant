package ru.itmo.eduassistant.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.eduassistant.backend.entity.Dialog;
import ru.itmo.eduassistant.backend.entity.Message;
import ru.itmo.eduassistant.backend.repository.DialogRepository;
import ru.itmo.eduassistant.backend.repository.MessageRepository;
import ru.itmo.eduassistant.commons.dto.question.NewMessageRequest;
import ru.itmo.eduassistant.commons.exception.EntityNotFoundException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DialogService {

    private final DialogRepository dialogRepository;
    private final MessageRepository messageRepository;

    public long addMessage(NewMessageRequest newMessageRequest) {
        Dialog dialog = dialogRepository.findById(newMessageRequest.dialogId())
                .orElseThrow(() -> new EntityNotFoundException("Dialog with id %s not found".formatted(newMessageRequest.dialogId())));
        Message newMessage = buildMessage(newMessageRequest, dialog);

        dialog.getMessages().add(newMessage);
        return newMessage.getId();
    }

    private Message buildMessage(NewMessageRequest request, Dialog dialog) {
        return messageRepository.save(Message.builder()
                .body(request.text())
                .datetime(LocalDateTime.now())
                .author(dialog.getAuthor())
                .recipient(dialog.getRecipient())
                .dialog(dialog).build()
        );
    }
}
