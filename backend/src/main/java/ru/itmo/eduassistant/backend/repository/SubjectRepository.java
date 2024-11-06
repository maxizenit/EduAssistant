package ru.itmo.eduassistant.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itmo.eduassistant.backend.entity.Channel;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Channel, Long> {
    @Query("SELECT s FROM Subject s WHERE s.teacher.id = :userId")
    List<Channel> findSubjectsByUserId(@Param("userId") Long userId);
}
