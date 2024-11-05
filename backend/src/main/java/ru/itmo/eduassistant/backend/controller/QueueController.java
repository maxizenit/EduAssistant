package ru.itmo.eduassistant.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.eduassistant.commons.dto.queue.StudentRequest;

@RestController("/queue")
@RequiredArgsConstructor
public class QueueController {

    @PostMapping("/")
    void create() {

    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {

    }

    @GetMapping()
    void getAllStudentsQueue(@RequestParam long studentId) {

    }

    @PostMapping("/{id}/students")
    void addNewStudent(@PathVariable long id, @RequestBody StudentRequest studentRequest) {

    }

    @DeleteMapping("/{id}/students/{studentId}")
    void deleteStudentFromQueue(@PathVariable long id, @PathVariable long studentId) {

    }
}
