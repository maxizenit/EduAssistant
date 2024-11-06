package ru.itmo.eduassistant.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.eduassistant.backend.entity.*;
import ru.itmo.eduassistant.backend.repository.*;
import ru.itmo.eduassistant.backend.service.SubjectService;
import ru.itmo.eduassistant.commons.dto.notification.AllNotificationsResponse;
import ru.itmo.eduassistant.commons.dto.notification.NotificationResponse;
import ru.itmo.eduassistant.commons.dto.notofication.NotificationStatus;
import ru.itmo.eduassistant.commons.dto.subject.QuestionRequest;
import ru.itmo.eduassistant.commons.exception.DialogNotFoundException;
import ru.itmo.eduassistant.commons.exception.EntityNotFoundException;
import ru.itmo.eduassistant.commons.exception.SubjectNotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    //TODO вынести вызовы в сервисы

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final MessageRepository messageRepository;
    private final DialogRepository dialogRepository;

    @Override
    public List<Subject> getAllSubjectsByStudent(long studentId) {
        long groupId = userRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("User with id %s not found".formatted(studentId)))
                .getGroup()
                .getId();

        return groupRepository.findSubjectsByGroupId(groupId);
    }

    @Override
    public List<Subject> getAllTeachersSubjects(long teacherId) {
        return subjectRepository.findSubjectsByUserId(teacherId);
    }

    @Override
    public Subject getSubject(long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException("Subject with id %s not found".formatted(id)));
    }

    @Override
    public AllNotificationsResponse getAllNotifications(long id, NotificationStatus status) {
        Subject subject = getSubject(id);
        List<Notification> notificationList = subject.getNotifications();

        List<NotificationResponse> notificationResponseList = notificationList
                .stream()
                .map(notif ->
                        new NotificationResponse(notif.getId(), notif.getBody(), notif.getDatetime()))
                .toList();

        return new AllNotificationsResponse(subject.getName(), notificationResponseList);
    }

    @Override
    public void createQuestion(long id, QuestionRequest request) {
        Subject subject = getSubject(id);
        User author = userRepository.findById(request.studentId())
                .orElseThrow(() ->
                        new EntityNotFoundException("User with id %s not found".formatted(request.studentId())));


        Message message = new Message();
        message.setAuthor(author);
        message.setRecipient(subject.getTeacher());
        message.setBody(request.text());
        message.setDatetime(LocalDateTime.now());

        Dialog dialog = new Dialog()
                .setSubject(subject)
                .setMessages(Collections.singletonList(message))
                .setFirstMessage(message.getBody())
                .setAuthor(author)
                .setRecipient(subject.getTeacher())
                .setIsClosed(false);

        dialogRepository.save(dialog);
        message.setDialog(dialog);
        messageRepository.save(message);
    }

    @Override
    public List<String> getAllQuestions(long id) {
        Subject subject = getSubject(id);

        return subject.getDialogs()
                .stream()
                .map(Dialog::getFirstMessage)
                .toList();
    }

    @Override
    public void markAsDiscussed(long id, long dialogId) {
        Subject subject = getSubject(id);

        Dialog questionDialog = subject.getDialogs()
                .stream()
                .filter(dialog -> dialog.getId() == dialogId)
                .findFirst()
                .orElseThrow(() ->
                        new DialogNotFoundException("User with id %s not found".formatted(dialogId)));

        questionDialog.setIsClosed(true);
        dialogRepository.save(questionDialog);
    }
}
