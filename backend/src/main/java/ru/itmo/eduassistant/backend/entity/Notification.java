package ru.itmo.eduassistant.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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