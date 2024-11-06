package ru.itmo.eduassistant.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itmo.eduassistant.backend.entity.Queue;
import ru.itmo.eduassistant.backend.entity.User;

import java.util.List;

public interface QueueRepository extends JpaRepository<Queue, Long> {
    @Query("SELECT q FROM Queue q JOIN q.users u WHERE u = :user")
    List<Queue> findByUsers(@Param("user") User user);
}
