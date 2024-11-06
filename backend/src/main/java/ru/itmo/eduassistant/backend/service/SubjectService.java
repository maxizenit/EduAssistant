package ru.itmo.eduassistant.backend.service;

import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.eduassistant.backend.entity.Subject;
import ru.itmo.eduassistant.commons.dto.notification.AllNotificationsResponse;
import ru.itmo.eduassistant.commons.dto.notofication.NotificationStatus;
import ru.itmo.eduassistant.commons.dto.subject.QuestionRequest;

import java.util.List;

public interface SubjectService {
    List<Subject> getAllSubjectsByStudent(long studentId);

    List<Subject> getAllTeachersSubjects(long teacherId);

    Subject getSubject(long id);

    AllNotificationsResponse getAllNotifications(long id, NotificationStatus status);

    void createQuestion(long id, QuestionRequest request);

    List<String> getAllQuestions(@PathVariable long id);

    void markAsDiscussed(long id, long dialogId);
}
