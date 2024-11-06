package ru.itmo.eduassistant.backend.entity.queue;


import jakarta.persistence.*;
import ru.itmo.eduassistant.backend.entity.subject.Subject;
import ru.itmo.eduassistant.backend.entity.user.User;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "queues")
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime expirationDate;

    private String name;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToMany
    @JoinTable(
            name = "queues_to_users",
            joinColumns = @JoinColumn(name = "queue_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))

    private Set<User> users;
}
