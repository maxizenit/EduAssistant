package ru.itmo.eduassistant.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.eduassistant.backend.service.impl.NotificationService;
import ru.itmo.eduassistant.commons.dto.notification.CreateNotificationRequest;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public void createNotification(@RequestBody CreateNotificationRequest createNotificationRequest) {
        notificationService.createNotification(createNotificationRequest);
    }
}
