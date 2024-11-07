package ru.itmo.eduassistant.backend.service;

import ru.itmo.eduassistant.backend.entity.Queue;
import ru.itmo.eduassistant.backend.entity.User;
import ru.itmo.eduassistant.commons.dto.user.NextUserResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface QueueService {
    long createQueue(long channelId, String name, LocalDateTime expirationDate);

    void deleteQueue(long id);

    Queue getQueueById(long id);

    List<Queue> getAllStudentQueues(long studentId);

    List<Queue> getAllTeacherQueues(long teacherId);

    void addStudentToQueue(long queueId, long studentId);

    void removeStudentFromQueue(long queueId, long studentId);
    NextUserResponse getNext(long queueId);
    List<User> getAllStudentsInQueue(long queueId);

    List<Queue> getAllQueues();
}