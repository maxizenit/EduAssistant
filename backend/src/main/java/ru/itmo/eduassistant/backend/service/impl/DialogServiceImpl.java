package ru.itmo.eduassistant.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.eduassistant.backend.entity.Channel;
import ru.itmo.eduassistant.backend.entity.Dialog;
import ru.itmo.eduassistant.backend.entity.Message;
import ru.itmo.eduassistant.backend.entity.User;
import ru.itmo.eduassistant.backend.model.UserRole;
import ru.itmo.eduassistant.backend.repository.ChannelRepository;
import ru.itmo.eduassistant.backend.repository.DialogRepository;
import ru.itmo.eduassistant.backend.repository.MessageRepository;
import ru.itmo.eduassistant.backend.repository.UserRepository;
import ru.itmo.eduassistant.commons.dto.dialog.NewMessageRequest;
import ru.itmo.eduassistant.commons.dto.dialog.NewQuestionRequest;
import ru.itmo.eduassistant.commons.exception.DialogNotFoundException;
import ru.itmo.eduassistant.commons.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DialogServiceImpl {

    private final DialogRepository dialogRepository;
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    public Message addMessage(NewMessageRequest newMessageRequest) {
        Dialog dialog = dialogRepository.findById(newMessageRequest.dialogId())
                .orElseThrow(() -> new EntityNotFoundException("Dialog with id %s not found".formatted(newMessageRequest.dialogId())));
        Message newMessage = buildMessage(newMessageRequest, dialog);

        dialog.getMessages().add(newMessage);
        return newMessage;
    }

    public Message createQuestion(NewQuestionRequest request) {
        Channel channel = channelRepository.getReferenceById(request.channelId());
        User author = userRepository.findById(request.studentId())
                .orElseThrow(() ->
                        new EntityNotFoundException("User with id %s not found".formatted(request.studentId())));


        Message message = new Message();
        message.setAuthor(author);
        message.setRecipient(channel.getTeacher());
        message.setBody(request.text());
        message.setDatetime(LocalDateTime.now());

        Dialog dialog = new Dialog()
                .setChannel(channel)
                .setMessages(Collections.singletonList(message))
                .setFirstMessage(message.getBody())
                .setAuthor(author)
                .setRecipient(channel.getTeacher())
                .setIsClosed(false);

        dialogRepository.save(dialog);
        message.setDialog(dialog);
        return messageRepository.save(message);
    }

    public List<Dialog> getAllQuestions(long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id %s not found".formatted(userId))
        );
        return user.getRole() == UserRole.STUDENT ? user.getAuthoredDialogs() : user.getReceivedDialogs();
    }

    public void markAsDiscussed(long dialogId) {
        Dialog dialog = dialogRepository.findById(dialogId).orElseThrow(() ->
                new DialogNotFoundException("Dialog with id %s not found".formatted(dialogId)));
        dialog.setIsClosed(true);
        dialogRepository.save(dialog);
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
