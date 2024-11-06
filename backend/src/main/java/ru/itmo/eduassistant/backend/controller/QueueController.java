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

@RestController
@RequestMapping("/queue")
@RequiredArgsConstructor
public class QueueController {

    @PostMapping("/")
    void create() {

    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {

    }

    @GetMapping("/student/{studentId}")
    public AllStudentQueuesResponse getAllStudentsQueue(@RequestParam long studentId) {
        return null;
    }

    @GetMapping("/teacher/teacherId")
    public AllTeacherQueuesResponse getAllTeachersQueues(@RequestParam long teacherId) {
        return null;
    }

    @PostMapping("/{id}/students")
    public void  addNewStudent(@PathVariable long id, @RequestParam long studentId) {

    }

    @DeleteMapping("/{id}/students/{studentId}")
    public void deleteStudentFromQueue(@PathVariable long id, @PathVariable long studentId) {

    }
}
