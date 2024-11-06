package ru.itmo.eduassistant.backend.repository.queue;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.eduassistant.backend.entity.queue.Queue;

public interface QueueRepository extends JpaRepository<Queue, Long> {
}
