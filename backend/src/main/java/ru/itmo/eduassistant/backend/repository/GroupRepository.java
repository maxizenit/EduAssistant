package ru.itmo.eduassistant.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.eduassistant.backend.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
