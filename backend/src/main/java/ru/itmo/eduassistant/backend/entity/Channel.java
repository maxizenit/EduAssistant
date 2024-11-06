package ru.itmo.eduassistant.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "channels")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @OneToMany(mappedBy = "channel")
    private List<Queue> queues;

    @OneToMany(mappedBy = "channel")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "channel")
    private List<Dialog> dialogs;

    @ManyToMany
    @JoinTable(
            name = "queues_to_users",
            joinColumns = @JoinColumn(name = "queue_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
}
