package ru.itmo.eduassistant.backend.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.eduassistant.backend.entity.Queue;
import ru.itmo.eduassistant.backend.entity.User;
import ru.itmo.eduassistant.backend.repository.QueueRepository;
import ru.itmo.eduassistant.backend.repository.SubjectRepository;
import ru.itmo.eduassistant.backend.repository.UserRepository;
import ru.itmo.eduassistant.backend.service.QueueService;
import ru.itmo.eduassistant.commons.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {
    private final SubjectRepository subjectRepository;
    private final QueueRepository queueRepository;
    private final UserRepository userRepository;

    @Override
    public long createQueue(long subjectId, String name, LocalDateTime expirationDate) {
        Queue queue = new Queue();
        queue.setName(name);
        queue.setSubject(subjectRepository.findById(subjectId).orElseThrow());
        queue.setExpirationDate(expirationDate);
        return queueRepository.save(queue).getId();
    }

    @Override
    public void deleteQueue(long id) {
        queueRepository.deleteById(id);
    }

    @Override
    public Queue getQueueById(long id) {
        return queueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Queue not found"));
    }

    @Override
    public List<Queue> getAllStudentQueues(long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        return queueRepository.findByUsers(student);
    }

    @Override
    public List<Queue> getAllTeacherQueues(long teacherId) {
        return queueRepository.findAll().stream()
                .filter(queue -> queue.getSubject().getTeacher().getId() == teacherId)
                .collect(Collectors.toList());
    }

    @Override
    public void addStudentToQueue(long queueId, long studentId) {
        Queue queue = queueRepository.findById(queueId)
                .orElseThrow(() -> new EntityNotFoundException("Queue not found"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        queue.getUsers().add(student);
        queueRepository.save(queue);
    }

    @Override
    public void removeStudentFromQueue(long queueId, long studentId) {
        Queue queue = queueRepository.findById(queueId)
                .orElseThrow(() -> new EntityNotFoundException("Queue not found"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        queue.getUsers().remove(student);
        queueRepository.save(queue);
    }
}
