package ru.itmo.eduassistant.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itmo.eduassistant.backend.entity.Group;
import ru.itmo.eduassistant.backend.entity.Subject;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT g.subjects FROM Group g WHERE g.id = :groupId")
    List<Subject> findSubjectsByGroupId(@Param("groupId") Long groupId);
}
