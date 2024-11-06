package ru.itmo.eduassistant.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.eduassistant.backend.entity.*;
import ru.itmo.eduassistant.backend.repository.*;
import ru.itmo.eduassistant.backend.service.ChannelService;
import ru.itmo.eduassistant.commons.dto.notification.AllNotificationsResponse;
import ru.itmo.eduassistant.commons.dto.notification.NotificationResponse;
import ru.itmo.eduassistant.commons.dto.notofication.NotificationStatus;
import ru.itmo.eduassistant.commons.dto.channel.QuestionRequest;
import ru.itmo.eduassistant.commons.exception.DialogNotFoundException;
import ru.itmo.eduassistant.commons.exception.EntityNotFoundException;
import ru.itmo.eduassistant.commons.exception.ChannelNotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    //TODO вынести вызовы в сервисы
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final DialogRepository dialogRepository;

    @Override
    public List<Channel> getAllChannelByStudent(long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found".formatted(studentId)));
        return student.getChannel();
    }

    @Override
    public List<Channel> getAllTeachersChannel(long teacherId) {
        return channelRepository.findChannelsByUserId(teacherId);
    }

    @Override
    public Channel getChannel(long id) {
        return channelRepository.findById(id)
                .orElseThrow(() -> new ChannelNotFoundException("Subject with id %s not found".formatted(id)));
    }

    @Override
    public AllNotificationsResponse getAllNotifications(long id, NotificationStatus status) {
        Channel channel = getChannel(id);
        List<Notification> notificationList = channel.getNotifications();

        List<NotificationResponse> notificationResponseList = notificationList
                .stream()
                .map(notif ->
                        new NotificationResponse(notif.getId(), notif.getBody(), notif.getDatetime()))
                .toList();

        return new AllNotificationsResponse(channel.getName(), notificationResponseList);
    }

    @Override
    public void createQuestion(long id, QuestionRequest request) {
        Channel channel = getChannel(id);
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
        messageRepository.save(message);
    }

    @Override
    public List<String> getAllQuestions(long id) {
        Channel channel = getChannel(id);

        return channel.getDialogs()
                .stream()
                .map(Dialog::getFirstMessage)
                .toList();
    }

    @Override
    public void markAsDiscussed(long id, long dialogId) {
        Channel channel = getChannel(id);

        Dialog questionDialog = channel.getDialogs()
                .stream()
                .filter(dialog -> dialog.getId() == dialogId)
                .findFirst()
                .orElseThrow(() ->
                        new DialogNotFoundException("User with id %s not found".formatted(dialogId)));

        questionDialog.setIsClosed(true);
        dialogRepository.save(questionDialog);
    }
}
