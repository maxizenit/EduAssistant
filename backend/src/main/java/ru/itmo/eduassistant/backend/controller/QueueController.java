package ru.itmo.eduassistant.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.eduassistant.commons.dto.queue.AllStudentQueuesResponse;
import ru.itmo.eduassistant.commons.dto.queue.AllTeacherQueuesResponse;
import ru.itmo.eduassistant.commons.dto.queue.QueueResponse;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/queue")
@RequiredArgsConstructor
public class QueueController {

    @PostMapping("/")
    public long create(@RequestParam long SubjectId, @RequestParam LocalDateTime expirationDate) {
        return 1L;
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {

    }

    @GetMapping("/{id}")
    public QueueResponse get(@PathVariable long id) {
        return null;
    }

    @GetMapping("/student/{studentId}")
    public AllStudentQueuesResponse getAllStudentsQueue(@PathVariable long studentId) {
        return null;
    }

    @GetMapping("/teacher/{teacherId}")
    public AllTeacherQueuesResponse getAllTeachersQueues(@PathVariable long teacherId) {
        return null;
    }

    @PostMapping("/{id}/students")
    public void  addNewStudent(@PathVariable long id, @RequestParam long studentId) {

    }

    @DeleteMapping("/{id}/students/{studentId}")
    public void deleteStudentFromQueue(@PathVariable long id, @PathVariable long studentId) {

    }
}
