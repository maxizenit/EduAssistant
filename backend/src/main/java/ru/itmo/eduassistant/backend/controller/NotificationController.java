package ru.itmo.eduassistant.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.eduassistant.backend.entity.Notification;
import ru.itmo.eduassistant.backend.service.impl.NotificationService;
import ru.itmo.eduassistant.commons.dto.notification.CreateNotificationRequest;
import ru.itmo.eduassistant.commons.dto.notification.NotificationResponse;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public NotificationResponse createNotification(@RequestBody CreateNotificationRequest createNotificationRequest) {
        Notification notif = notificationService.createNotification(createNotificationRequest);
        return new NotificationResponse(notif.getId(), notif.getBody(), notif.getDatetime());
    }
}
