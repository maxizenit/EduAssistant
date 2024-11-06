package ru.itmo.eduassistant.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.eduassistant.backend.entity.Template;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}
