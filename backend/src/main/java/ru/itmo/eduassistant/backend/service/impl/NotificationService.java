package ru.itmo.eduassistant.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.eduassistant.backend.entity.Channel;
import ru.itmo.eduassistant.backend.entity.Notification;
import ru.itmo.eduassistant.backend.model.NotificationType;
import ru.itmo.eduassistant.backend.repository.ChannelRepository;
import ru.itmo.eduassistant.backend.repository.NotificationRepository;
import ru.itmo.eduassistant.commons.dto.notification.CreateNotificationRequest;
import ru.itmo.eduassistant.commons.exception.EntityNotFoundException;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ChannelRepository channelRepository;

    public void createNotification(CreateNotificationRequest request) {
        Notification notification = build(request);
        notificationRepository.save(notification);
    }

    private Notification build(CreateNotificationRequest request) {
        NotificationType type =  NotificationType.valueOf(request.type());
        Channel channel = channelRepository.findById(request.channelId())
                .orElseThrow(() -> new EntityNotFoundException("Channel with id %s not found".formatted(request.channelId())));

        return Notification.builder()
                .body(type.apply(channel.getName(), request.text()))
                .datetime(LocalDateTime.now())
                .channel(channel)
                .isArchived(false)
                .build();
    }
}
