package ru.itmo.eduassistant.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.eduassistant.backend.entity.Queue;

public interface QueueRepository extends JpaRepository<Queue, Long> {
}
