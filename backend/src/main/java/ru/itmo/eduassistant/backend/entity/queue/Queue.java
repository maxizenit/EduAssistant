package ru.itmo.eduassistant.backend.entity.queue;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.eduassistant.backend.entity.subject.Subject;
import ru.itmo.eduassistant.backend.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
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

    private List<User> users;
}
