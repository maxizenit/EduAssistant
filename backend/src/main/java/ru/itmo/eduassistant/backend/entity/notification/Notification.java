package ru.itmo.eduassistant.backend.entity.notification;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.eduassistant.backend.entity.subject.Subject;
import ru.itmo.eduassistant.backend.entity.template.Template;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    private LocalDateTime datetime;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private Boolean isArchived;
}
