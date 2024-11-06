package ru.itmo.eduassistant.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.eduassistant.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
