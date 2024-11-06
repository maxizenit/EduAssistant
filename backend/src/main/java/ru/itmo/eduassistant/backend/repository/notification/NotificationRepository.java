package ru.itmo.eduassistant.backend.repository.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.eduassistant.backend.entity.notification.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
