package ru.itmo.eduassistant.backend.entity.group;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.eduassistant.backend.entity.subject.Subject;
import ru.itmo.eduassistant.backend.entity.user.User;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "groups_to_subjects",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))

    private List<Subject> subjects;

    @OneToMany(mappedBy = "group")
    private List<User> users;
}
