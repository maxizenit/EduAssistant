package ru.itmo.eduassistant.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itmo.eduassistant.backend.entity.Channel;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    @Query("SELECT c FROM Channel c WHERE c.teacher.telegramId = :telegramUserId")
    List<Channel> findChannelsByUserTelegramId(@Param("telegramUserId") Long telegramUserId);
    Optional<Channel> findChannelByName(String name);
}
