package ru.itmo.eduassistant.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.eduassistant.backend.mapper.SubjectMapper;
import ru.itmo.eduassistant.backend.service.SubjectService;
import ru.itmo.eduassistant.backend.service.impl.DialogService;
import ru.itmo.eduassistant.commons.dto.notification.AllNotificationsResponse;
import ru.itmo.eduassistant.commons.dto.notofication.NotificationStatus;
import ru.itmo.eduassistant.commons.dto.question.AllQuestionsResponse;
import ru.itmo.eduassistant.commons.dto.question.NewMessageRequest;
import ru.itmo.eduassistant.commons.dto.question.NewMessageResponse;
import ru.itmo.eduassistant.commons.dto.subject.AllSubjectsResponse;
import ru.itmo.eduassistant.commons.dto.subject.QuestionRequest;
import ru.itmo.eduassistant.commons.dto.subject.SubjectResponse;

import java.util.List;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectMapper subjectMapper;
    private final SubjectService service;
    private final DialogService dialogService;

    @GetMapping
    public AllSubjectsResponse getAllStudentsSubjects(@RequestParam long studentId) {
        List<SubjectResponse> response = service.getAllSubjectsByStudent(studentId)
                .stream()
                .map(subjectMapper::toResponse)
                .toList();

        return new AllSubjectsResponse(response);
    }

    @GetMapping("/teacher/{teacherId}")
    public AllSubjectsResponse getAllTeachersSubjects(@PathVariable long teacherId) {
        List<SubjectResponse> response = service.getAllTeachersSubjects(teacherId)
                .stream()
                .map(subjectMapper::toResponse)
                .toList();

        return new AllSubjectsResponse(response);
    }

    @GetMapping("/{id}")
    public SubjectResponse getSubject(@PathVariable long id) {
        return subjectMapper.toResponse(service.getSubject(id));
    }

    @GetMapping("/{id}/notifications")
    public AllNotificationsResponse getAllNotifications(@PathVariable long id,
                                                        @RequestParam NotificationStatus status) {
        return service.getAllNotifications(id, status);
    }

    @PostMapping("/{id}/questions")
    void createQuestion(@PathVariable long id, @RequestBody QuestionRequest request) {
        service.createQuestion(id, request);
    }

    @PostMapping("/dialog/{dialogId}")
    public NewMessageResponse addMessageToDialog(@RequestBody NewMessageRequest newMessageRequest) {
        return new NewMessageResponse(dialogService.addMessage(newMessageRequest));
    }

    @GetMapping("/{id}/questions")
    public AllQuestionsResponse getAllQuestions(@PathVariable long id) {
        return new AllQuestionsResponse(service.getAllQuestions(id));
    }

    @PostMapping("/{id}/questions/{dialogId}")
    void markAsDiscussed(@PathVariable long id, @PathVariable long dialogId) {
        service.markAsDiscussed(id, dialogId);
    }
}
