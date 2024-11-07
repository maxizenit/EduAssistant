package ru.itmo.eduassistant.backend.service;

import ru.itmo.eduassistant.backend.entity.Channel;
import ru.itmo.eduassistant.commons.dto.channel.CreateChannelRequest;
import ru.itmo.eduassistant.commons.dto.notification.AllNotificationsResponse;
import ru.itmo.eduassistant.commons.dto.notofication.NotificationStatus;

import java.util.List;

public interface ChannelService {
    List<Channel> getAllChannelByStudent(long studentId);

    List<Channel> getAllTeachersChannel(long teacherId);

    Channel getChannel(long id);

    AllNotificationsResponse getAllNotifications(long id, NotificationStatus status);

    Channel createChannel(CreateChannelRequest createChannelRequest);

    void addUserToChannel(long channelId, long telegramUserId);

    void deleteUserFromChannel(long channelId, long telegramUserId);
}
