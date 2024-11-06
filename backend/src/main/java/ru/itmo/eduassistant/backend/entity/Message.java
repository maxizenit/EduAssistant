package ru.itmo.eduassistant.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;

    private LocalDateTime datetime;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @ManyToOne
    @JoinColumn(name = "recipient")
    private User recipient;

    @ManyToOne
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;
}