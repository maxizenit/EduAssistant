package ru.itmo.eduassistant.backend.service;

import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.eduassistant.backend.entity.Channel;
import ru.itmo.eduassistant.commons.dto.notification.AllNotificationsResponse;
import ru.itmo.eduassistant.commons.dto.notofication.NotificationStatus;
import ru.itmo.eduassistant.commons.dto.channel.QuestionRequest;

import java.util.List;

public interface ChannelService {
    List<Channel> getAllChannelByStudent(long studentId);

    List<Channel> getAllTeachersChannel(long teacherId);

    Channel getChannel(long id);

    AllNotificationsResponse getAllNotifications(long id, NotificationStatus status);

    void createQuestion(long id, QuestionRequest request);

    List<String> getAllQuestions(@PathVariable long id);

    void markAsDiscussed(long id, long dialogId);
}
