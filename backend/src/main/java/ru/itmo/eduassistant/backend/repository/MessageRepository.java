package ru.itmo.eduassistant.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.eduassistant.backend.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
