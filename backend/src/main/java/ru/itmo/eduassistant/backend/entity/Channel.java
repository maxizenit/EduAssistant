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

    @Column(name = "description")
    private String description;

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
            name = "users_to_channel",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
}
