package ru.itmo.eduassistant.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String role;

    private String fio;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToMany(mappedBy = "users")
    private List<Queue> queues;

    @OneToMany(mappedBy = "author")
    private List<Dialog> authoredDialogs;

    @OneToMany(mappedBy = "recipient")
    private List<Dialog> receivedDialogs;

    @OneToMany(mappedBy = "author")
    private List<Message> authoredMessages;

    @OneToMany(mappedBy = "recipient")
    private List<Message> receivedMessages;
}
