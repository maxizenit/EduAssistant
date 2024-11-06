package ru.itmo.eduassistant.backend.entity.dialog;

import jakarta.persistence.*;
import ru.itmo.eduassistant.backend.entity.message.Message;
import ru.itmo.eduassistant.backend.entity.subject.Subject;
import ru.itmo.eduassistant.backend.entity.user.User;

import java.util.List;

@Entity
@Table(name = "dialogs")
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isClosed;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;


    @JoinColumn(name = "first_message")
    private Long firstMessageId;

    @ManyToOne
    @JoinColumn(name = "recipient")
    private User recipient;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToMany(mappedBy = "dialog")
    private List<Message> messages;
}
