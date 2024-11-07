package ru.itmo.eduassistant.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.eduassistant.backend.mapper.NotificationMapper;
import ru.itmo.eduassistant.backend.service.impl.NotificationService;
import ru.itmo.eduassistant.commons.dto.notification.CreateNotificationRequest;
import ru.itmo.eduassistant.commons.dto.notification.NotificationResponse;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @PostMapping
    public NotificationResponse createNotification(@RequestBody CreateNotificationRequest createNotificationRequest) {
        var notification = notificationService.createNotification(createNotificationRequest);
        return notificationMapper.toResponse(notification);
    }

    @GetMapping
    public List<NotificationResponse> getAllNotifications(@RequestParam Long telegramId) {
        return notificationService.getAllNotifications(telegramId).stream()
                .map(notificationMapper::toResponse).toList();
    }

}
