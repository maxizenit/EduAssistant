package ru.itmo.eduassistant.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.eduassistant.backend.model.NotificationStatus;
import ru.itmo.eduassistant.commons.dto.subject.QuestionRequest;

@RestController("/subject")
@RequiredArgsConstructor
public class SubjectController {

    @GetMapping("/")
    void getAllSubjects(@RequestParam long studentId) {

    }

    @GetMapping("/{id}")
    void getSubject(@PathVariable long id) {

    }

    @PostMapping("/{id}/notifications")
    void createNotification(@PathVariable long id) {

    }

    @PostMapping("/{id}/notifications")
    void getAllNotifications(@PathVariable long id, @RequestParam NotificationStatus status) {

    }

    @PostMapping("/{id}/questions")
    void createQuestion(@PathVariable long id, @RequestBody QuestionRequest request) {

    }

    @GetMapping("/{id}/questions")
    void getAllQuestions(@PathVariable long id) {

    }

    @PostMapping("/{id}/questions/{questionId}")
    void markAsDiscussed(@PathVariable long id, @PathVariable long questionId) {

    }
}
