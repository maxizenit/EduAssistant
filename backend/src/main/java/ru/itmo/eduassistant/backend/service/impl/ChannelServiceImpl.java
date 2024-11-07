package ru.itmo.eduassistant.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.eduassistant.backend.entity.Channel;
import ru.itmo.eduassistant.backend.entity.Notification;
import ru.itmo.eduassistant.backend.entity.User;
import ru.itmo.eduassistant.backend.mapper.NotificationMapper;
import ru.itmo.eduassistant.backend.repository.ChannelRepository;
import ru.itmo.eduassistant.backend.service.ChannelService;
import ru.itmo.eduassistant.backend.service.UserService;
import ru.itmo.eduassistant.commons.dto.channel.AllStudentsInChannelResponse;
import ru.itmo.eduassistant.commons.dto.channel.CreateChannelRequest;
import ru.itmo.eduassistant.commons.dto.channel.StudentInChannelResponse;
import ru.itmo.eduassistant.commons.dto.notification.AllNotificationsResponse;
import ru.itmo.eduassistant.commons.dto.notification.NotificationResponse;
import ru.itmo.eduassistant.commons.exception.ChannelNotFoundException;
import ru.itmo.eduassistant.commons.exception.ConflictException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    private final UserService userService;
    private final ChannelRepository channelRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public List<Channel> getAllChannelByStudent(long studentId) {
        return userService.getUserByTelegramId(studentId).getChannel();
    }

    @Override
    public List<Channel> getAllTeachersChannel(long telegramTeacherId) {
        return channelRepository.findChannelsByUserTelegramId(telegramTeacherId);
    }

    @Override
    public Channel getChannel(long id) {
        return channelRepository.findById(id)
                .orElseThrow(() -> new ChannelNotFoundException("Channel with id %s not found".formatted(id)));
    }

    @Override
    public AllNotificationsResponse getAllNotifications(long id) {
        Channel channel = getChannel(id);
        List<Notification> notificationList = channel.getNotifications();

        List<NotificationResponse> notificationResponseList = notificationList.stream()
                .map(notificationMapper::toResponse).toList();

        return new AllNotificationsResponse(channel.getName(), notificationResponseList);
    }

    @Override
    public Channel createChannel(CreateChannelRequest createChannelRequest) {
        Channel channel = new Channel();
        channel.setTeacher(userService.getUserByTelegramId(createChannelRequest.teacherId()));
        channel.setName(createChannelRequest.name());
        return channelRepository.save(channel);
    }

    @Override
    public void addUserToChannel(long channelId, long telegramUserId) {
        User user = userService.getUserByTelegramId(telegramUserId);

        Channel channel = getChannel(channelId);

        if (channel.getUsers()
                .stream()
                .noneMatch(filteredUser -> filteredUser.getTelegramId() == telegramUserId)) {
            channel.getUsers().add(user);
            channelRepository.save(channel);
        } else {
            throw new ConflictException("Such user already exists in channel");
        }

    }

    @Override
    public void deleteUserFromChannel(long channelId, long telegramUserId) {
        User user = userService.getUserByTelegramId(telegramUserId);

        Channel channel = getChannel(channelId);

        if (channel.getUsers()
                .stream()
                .anyMatch(filteredUser -> filteredUser.getTelegramId() == telegramUserId)) {
            channel.getUsers().remove(user);
            channelRepository.save(channel);
        } else {
            throw new ConflictException("There is no such user in channel");
        }
    }

    @Override
    public AllStudentsInChannelResponse getAllStudents(long id) {
        Channel channel = getChannel(id);

        List<StudentInChannelResponse> students = channel.getUsers()
                .stream()
                .map(User::getFio)
                .map(StudentInChannelResponse::new)
                .toList();

        return new AllStudentsInChannelResponse(students);
    }
}
