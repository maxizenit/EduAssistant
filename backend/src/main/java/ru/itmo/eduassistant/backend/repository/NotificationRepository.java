package ru.itmo.eduassistant.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.eduassistant.backend.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
