package ru.itmo.eduassistant.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.eduassistant.backend.entity.*;
import ru.itmo.eduassistant.backend.repository.*;
import ru.itmo.eduassistant.backend.service.ChannelService;
import ru.itmo.eduassistant.commons.dto.notification.AllNotificationsResponse;
import ru.itmo.eduassistant.commons.dto.notification.NotificationResponse;
import ru.itmo.eduassistant.commons.dto.notofication.NotificationStatus;
import ru.itmo.eduassistant.commons.exception.EntityNotFoundException;
import ru.itmo.eduassistant.commons.exception.ChannelNotFoundException;

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
        return channelRepository.findChannelsByTeacherId(teacherId);
    }

    @Override
    public Channel getChannel(long id) {
        return channelRepository.findById(id)
                .orElseThrow(() -> new ChannelNotFoundException("Channel with id %s not found".formatted(id)));
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
}
