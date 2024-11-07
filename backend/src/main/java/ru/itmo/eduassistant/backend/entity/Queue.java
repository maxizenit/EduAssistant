package ru.itmo.eduassistant.backend.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @ManyToMany
    @JoinTable(
            name = "queues_to_users",
            joinColumns = @JoinColumn(name = "queue_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @Column(name = "position")
    private int position;

    @Nullable
    public User getCurrentUserAndIncrementPosition() {
        if (position >= users.size()) {
            return null;
        }
       return users.get(position++);
    }

    public List<User> getTailOfQueue() {
        return new ArrayList<>(users.subList(position, users.size()));
    }
}
