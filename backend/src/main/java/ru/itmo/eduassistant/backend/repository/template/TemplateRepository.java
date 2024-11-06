package ru.itmo.eduassistant.backend.repository.template;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.eduassistant.backend.entity.template.Template;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}
