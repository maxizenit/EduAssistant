package ru.itmo.eduassistant.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.eduassistant.backend.entity.Channel;
import ru.itmo.eduassistant.backend.entity.Queue;
import ru.itmo.eduassistant.backend.entity.User;
import ru.itmo.eduassistant.backend.model.NotificationType;
import ru.itmo.eduassistant.backend.repository.QueueRepository;
import ru.itmo.eduassistant.backend.service.ChannelService;
import ru.itmo.eduassistant.backend.service.QueueService;
import ru.itmo.eduassistant.backend.service.UserService;
import ru.itmo.eduassistant.commons.client.telegram.TelegramMessageClient;
import ru.itmo.eduassistant.commons.dto.notification.NotificationResponse;
import ru.itmo.eduassistant.commons.dto.telegram.SendNotificationRequest;
import ru.itmo.eduassistant.commons.dto.user.NextUserResponse;
import ru.itmo.eduassistant.commons.exception.ConflictException;
import ru.itmo.eduassistant.commons.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {
    private final ChannelService channelService;
    private final QueueRepository queueRepository;
    private final UserService userService;
    private final TelegramMessageClient telegramMessageClient;

    @Override
    public long createQueue(long channelId, String name, LocalDateTime expirationDate) {
        Queue queue = new Queue();
        queue.setName(name);
        Channel channel = channelService.getChannel(channelId);
        queue.setChannel(channel);
        queue.setExpirationDate(expirationDate);

        sendCreateNotifications(name, expirationDate, channel, queue);

        return queueRepository.save(queue).getId();
    }

    private void sendCreateNotifications(String name, LocalDateTime expirationDate, Channel channel, Queue queue) {
        telegramMessageClient.postNotifications(new SendNotificationRequest(
                channel.getUsers().stream().map(User::getTelegramId).toList(),
                new NotificationResponse(
                        queue.getId(),
                        NotificationType.QUEUE_OPENED.apply(queue.getName(), expirationDate.toString()),
                        name,
                        channel.getTeacher().getFio(),
                        LocalDateTime.now()
                )
        ));
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
        User student = userService.getUserByTelegramId(studentId);
        return queueRepository.findByUsers(student);
    }

    @Override
    public List<Queue> getAllTeacherQueues(long teacherId) {
        return queueRepository.findAll().stream()
                .filter(queue -> queue.getChannel().getTeacher().getTelegramId() == teacherId)
                .collect(Collectors.toList());
    }

    @Override
    public void addStudentToQueue(long queueId, long studentId) {
        Queue queue = queueRepository.findById(queueId)
                .orElseThrow(() -> new EntityNotFoundException("Queue not found"));
        User student = userService.getUserByTelegramId(studentId);

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
        User student = userService.getUserByTelegramId(studentId);

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
        NextUserResponse nextUserResponse = buildResponse(user, users, queue);

        sendNotificationsIfNeeded(queueId, nextUserResponse, queue);

        return nextUserResponse;
    }

    private void sendNotificationsIfNeeded(long queueId, NextUserResponse nextUserResponse, Queue queue) {
        if (nextUserResponse.getCurrent() != null) {
            telegramMessageClient.postNotifications(new SendNotificationRequest(
                    List.of(nextUserResponse.getCurrent().telegramId()),
                    new NotificationResponse(
                            queueId,
                            nextUserResponse.getMessageForCurrent(),
                            null,
                            queue.getChannel().getTeacher().getFio(),
                            LocalDateTime.now()
                    )

            ));
        }
        if (nextUserResponse.getNext() != null) {
            telegramMessageClient.postNotifications(new SendNotificationRequest(
                    List.of(nextUserResponse.getNext().telegramId()),
                    new NotificationResponse(
                            queueId,
                            nextUserResponse.getMessageForNext(),
                            null,
                            queue.getChannel().getTeacher().getFio(),
                            LocalDateTime.now()
                    )

            ));
        }
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

    @Override
    public List<Queue> getAllQueues() {
        return queueRepository.findAll();
    }
}
