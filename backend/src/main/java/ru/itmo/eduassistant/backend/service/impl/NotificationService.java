package ru.itmo.eduassistant.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.eduassistant.backend.entity.Channel;
import ru.itmo.eduassistant.backend.entity.Notification;
import ru.itmo.eduassistant.backend.entity.User;
import ru.itmo.eduassistant.backend.mapper.NotificationMapper;
import ru.itmo.eduassistant.backend.model.NotificationType;
import ru.itmo.eduassistant.backend.model.UserRole;
import ru.itmo.eduassistant.backend.repository.ChannelRepository;
import ru.itmo.eduassistant.backend.repository.NotificationRepository;
import ru.itmo.eduassistant.backend.repository.UserRepository;
import ru.itmo.eduassistant.commons.client.telegram.TelegramMessageClient;
import ru.itmo.eduassistant.commons.dto.notification.CreateNotificationRequest;
import ru.itmo.eduassistant.commons.dto.telegram.SendNotificationRequest;
import ru.itmo.eduassistant.commons.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final TelegramMessageClient telegramMessageClient;
    private final NotificationMapper mapper;

    public Notification createNotification(CreateNotificationRequest request) {
        Notification notification = notificationRepository.save(build(request));

        List<Long> telegramIds = notification.getChannel().getUsers().stream().map(User::getId).toList();
        telegramMessageClient.postNotifications(new SendNotificationRequest(telegramIds, mapper.toResponse(notification)));

        return notification;
    }

    private Notification build(CreateNotificationRequest request) {
        //TODO add proper mapper for NotificationType
        NotificationType type = NotificationType.valueOf(request.type().name());
        Channel channel = channelRepository.findById(request.channelId())
                .orElseThrow(() -> new EntityNotFoundException("Channel with id %s not found".formatted(request.channelId())));

        return Notification.builder()
                .body(type.apply(channel.getName(), request.text()))
                .datetime(LocalDateTime.now())
                .channel(channel)
                .isArchived(false)
                .build();
    }

    public List<Notification> getAllNotifications(Long telegramId) {
        var user = userRepository.findByTelegramId(telegramId).orElseThrow(() ->
                new EntityNotFoundException("User with id %s not found".formatted(telegramId)));
        return user.getRole() == UserRole.STUDENT ?
                notificationRepository.findStudentNotificationsByTelegramId(telegramId) :
                notificationRepository.findTeacherNotificationsByTelegramId(telegramId);
    }
}
