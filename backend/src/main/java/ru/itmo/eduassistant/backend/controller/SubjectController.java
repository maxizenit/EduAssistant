package ru.itmo.eduassistant.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.eduassistant.commons.dto.notofication.NotificationStatus;
import ru.itmo.eduassistant.commons.dto.notification.AllNotificationsResponse;
import ru.itmo.eduassistant.commons.dto.question.AllQuestionsResponse;
import ru.itmo.eduassistant.commons.dto.subject.AllSubjectsResponse;
import ru.itmo.eduassistant.commons.dto.subject.QuestionRequest;
import ru.itmo.eduassistant.commons.dto.subject.SubjectResponse;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {

    @GetMapping
    public AllSubjectsResponse getAllStudentsSubjects(@RequestParam long studentId) {
        return null;
    }

    @GetMapping("/teacher/{teacherId}")
    public AllSubjectsResponse getAllTeachersSubjects(@PathVariable long teacherId) {
        return null;
    }

    @GetMapping("/{id}")
    public SubjectResponse getSubject(@PathVariable long id) {
        return null;
    }

    @PostMapping("/{id}/notifications")
    public AllNotificationsResponse getAllNotifications(@PathVariable long id, @RequestParam NotificationStatus status) {
        return null;
    }

    @PostMapping("/{id}/questions")
    long createQuestion(@PathVariable long id, @RequestBody QuestionRequest request) {
        return 1L;
    }

    @GetMapping("/{id}/questions")
    public AllQuestionsResponse getAllQuestions(@PathVariable long id) {
        return null;
    }

    @PostMapping("/{id}/questions/{questionId}")
    void markAsDiscussed(@PathVariable long id, @PathVariable long questionId) {

    }
}