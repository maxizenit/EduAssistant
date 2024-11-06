package ru.itmo.eduassistant.backend.entity.subject;

import jakarta.persistence.*;
import ru.itmo.eduassistant.backend.entity.dialog.Dialog;
import ru.itmo.eduassistant.backend.entity.group.Group;
import ru.itmo.eduassistant.backend.entity.notification.Notification;
import ru.itmo.eduassistant.backend.entity.queue.Queue;
import ru.itmo.eduassistant.backend.entity.user.User;

import java.util.List;
import java.util.Set;

@Entity
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
    private Set<Group> groups;

    @OneToMany(mappedBy = "subject")
    private List<Dialog> dialogs;
}
