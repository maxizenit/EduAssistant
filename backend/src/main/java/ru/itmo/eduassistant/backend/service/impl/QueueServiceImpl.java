package ru.itmo.eduassistant.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import ru.itmo.eduassistant.backend.entity.Queue;
import ru.itmo.eduassistant.backend.entity.User;
import ru.itmo.eduassistant.backend.model.NotificationType;
import ru.itmo.eduassistant.backend.repository.QueueRepository;
import ru.itmo.eduassistant.backend.repository.ChannelRepository;
import ru.itmo.eduassistant.backend.repository.UserRepository;
import ru.itmo.eduassistant.backend.service.QueueService;
import ru.itmo.eduassistant.commons.dto.user.NextUserResponse;
import ru.itmo.eduassistant.commons.exception.ConflictException;
import ru.itmo.eduassistant.commons.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {
    private final ChannelRepository channelRepository;
    private final QueueRepository queueRepository;
    private final UserRepository userRepository;

    @Override
    public long createQueue(long channelId, String name, LocalDateTime expirationDate) {
        Queue queue = new Queue();
        queue.setName(name);
        queue.setChannel(channelRepository.findById(channelId).orElseThrow());
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
                .filter(queue -> queue.getChannel().getTeacher().getId() == teacherId)
                .collect(Collectors.toList());
    }

    @Override
    public void addStudentToQueue(long queueId, long studentId) {
        Queue queue = queueRepository.findById(queueId)
                .orElseThrow(() -> new EntityNotFoundException("Queue not found"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        if (queue.getUsers().contains(student)) {
            throw new ConflictException("User already present at queue");
        }

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

    @Override
    public NextUserResponse getNext(long queueId) {
        Queue queue = queueRepository.findById(queueId)
                .orElseThrow(() -> new EntityNotFoundException("Queue not found"));
        User user = queue.getCurrentUserAndIncrementPosition();
        List<User> users = queue.getTailOfQueue();

        queueRepository.save(queue);
        return buildResponse(user, users, queue);
    }

    private NextUserResponse buildResponse(User current, List<User> users, Queue queue) {
        var responseBuilder = NextUserResponse.builder();
        if (current != null) {
            responseBuilder.current(
                    new NextUserResponse.User(current.getTelegramId(), current.getFio()));
            responseBuilder.messageForCurrent(NotificationType.YOUR_TURN.apply(queue.getName()));
        }
        if (!users.isEmpty()) {
            responseBuilder.next(
                    new NextUserResponse.User(users.get(0).getTelegramId(), users.get(0).getFio()));
            responseBuilder.messageForNext(NotificationType.YOUR_TURN_NEXT.apply(queue.getName()));
            responseBuilder.queue(
                    users.stream()
                            .map(user -> new NextUserResponse.User(
                                    user.getTelegramId(),
                                    user.getFio()
                            )).toList()
            );
        }
       return responseBuilder.build();
    }

    public List<User> getAllStudentsInQueue(long queueId) {
        Queue queue = queueRepository.findById(queueId)
                .orElseThrow(() -> new EntityNotFoundException("Queue not found"));
        return queue.getUsers();
    }
}
