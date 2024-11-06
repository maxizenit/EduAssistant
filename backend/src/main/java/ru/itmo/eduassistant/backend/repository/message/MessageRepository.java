package ru.itmo.eduassistant.backend.repository.message;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.eduassistant.backend.entity.message.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
