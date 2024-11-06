package ru.itmo.eduassistant.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.eduassistant.backend.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
