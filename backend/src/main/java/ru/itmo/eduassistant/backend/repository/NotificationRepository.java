package ru.itmo.eduassistant.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itmo.eduassistant.backend.entity.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n " +
            "JOIN n.channel c " +
            "JOIN c.users u " +
            "WHERE u.telegramId = :telegramId")
    List<Notification> findStudentNotificationsByTelegramId(Long telegramId);

    @Query("SELECT n FROM Notification n " +
            "JOIN n.channel c " +
            "WHERE c.teacher.telegramId = :telegramId")
    List<Notification> findTeacherNotificationsByTelegramId(Long telegramId);
}
