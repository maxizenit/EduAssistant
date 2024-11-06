package ru.itmo.eduassistant.backend.entity.template;

import jakarta.persistence.*;

@Entity
@Table(name = "templates")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;
}
