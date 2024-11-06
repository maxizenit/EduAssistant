package ru.itmo.eduassistant.backend.repository.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.eduassistant.backend.entity.subject.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
