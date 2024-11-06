package ru.itmo.eduassistant.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itmo.eduassistant.backend.entity.Notification;
import ru.itmo.eduassistant.backend.entity.Subject;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT s FROM Subject s WHERE s.teacher.id = :userId")
    List<Subject> findSubjectsByUserId(@Param("userId") Long userId);
}
