package ru.itmo.eduassistant.backend.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.eduassistant.backend.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
