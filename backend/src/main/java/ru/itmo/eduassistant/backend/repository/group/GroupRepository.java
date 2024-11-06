package ru.itmo.eduassistant.backend.repository.group;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.eduassistant.backend.entity.group.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
