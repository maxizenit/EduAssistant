package ru.itmo.eduassistant.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @OneToMany(mappedBy = "subject")
    private List<Queue> queues;

    @OneToMany(mappedBy = "subject")
    private List<Notification> notifications;

    @ManyToMany(mappedBy = "subjects")
    private List<Group> groups;

    @OneToMany(mappedBy = "subject")
    private List<Dialog> dialogs;
}
