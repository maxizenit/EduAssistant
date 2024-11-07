package ru.itmo.eduassistant.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.eduassistant.backend.entity.Queue;
import ru.itmo.eduassistant.backend.mapper.QueueMapper;
import ru.itmo.eduassistant.backend.mapper.UserMapper;
import ru.itmo.eduassistant.backend.service.QueueService;
import ru.itmo.eduassistant.commons.dto.queue.*;
import ru.itmo.eduassistant.commons.dto.user.NextUserResponse;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/queue")
@RequiredArgsConstructor
public class QueueController {

    private final QueueMapper queueMapper;
    private final UserMapper userMapper;
    private final QueueService queueService;

    //    TODO мб поменять возвращаемое значение на QueueResponse
    @PostMapping
    public long create(@RequestParam long channelId, @RequestParam String name, @RequestParam LocalDateTime expirationDate) {
        return queueService.createQueue(channelId, name, expirationDate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        queueService.deleteQueue(id);
    }

    @GetMapping("/{id}")
    public QueueResponse get(@PathVariable long id) {
        Queue queue = queueService.getQueueById(id);
        return queueMapper.toQueueResponse(queue);
    }

    @GetMapping("/student/{studentId}")
    public AllStudentQueuesResponse getAllStudentsQueue(@PathVariable long studentId) {
        List<Queue> queues = queueService.getAllStudentQueues(studentId);
        List<QueueResponse> queueResponses = queueMapper.toQueueResponseList(queues);
        return new AllStudentQueuesResponse(queueResponses);
    }

    @GetMapping("/teacher/{teacherId}")
    public AllTeacherQueuesResponse getAllTeachersQueues(@PathVariable long teacherId) {
        List<Queue> queues = queueService.getAllTeacherQueues(teacherId);
        List<QueueResponse> queueResponses = queueMapper.toQueueResponseList(queues);
        return new AllTeacherQueuesResponse(queueResponses);
    }

    @PostMapping("/{id}/students")
    public void addNewStudent(@PathVariable long id, @RequestParam long studentId) {
        queueService.addStudentToQueue(id, studentId);
    }

    @DeleteMapping("/{id}/students/{studentId}")
    public void deleteStudentFromQueue(@PathVariable long id, @PathVariable long studentId) {
        queueService.removeStudentFromQueue(id, studentId);
    }

    @GetMapping("/{id}/students")
    public AllStudentInQueueResponse getAllStudentsInQueue(@PathVariable long id) {
        return userMapper.toResponse(queueService.getAllStudentsInQueue(id));
    }

    @GetMapping("/{id}/student/next")
    public NextUserResponse getCurrentStudent(@PathVariable long id) {
        return queueService.getNext(id);
    }

    @GetMapping
    public AllQueuesResponse getAllQueues() {
        return new AllQueuesResponse(queueMapper
                .toQueueResponseList(queueService
                        .getAllQueues())
        );
    }
}
