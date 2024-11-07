package ru.itmo.eduassistant.commons.client.backend;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;
import ru.itmo.eduassistant.commons.client.AbstractControllerHttpClient;
import ru.itmo.eduassistant.commons.dto.channel.AllChannelsResponse;
import ru.itmo.eduassistant.commons.dto.channel.AllStudentsInChannelResponse;
import ru.itmo.eduassistant.commons.dto.channel.ChannelResponse;
import ru.itmo.eduassistant.commons.dto.channel.CreateChannelRequest;
import ru.itmo.eduassistant.commons.dto.dialog.*;
import ru.itmo.eduassistant.commons.dto.notification.AllNotificationsResponse;
import ru.itmo.eduassistant.commons.dto.notification.CreateNotificationRequest;
import ru.itmo.eduassistant.commons.dto.notification.NotificationResponse;
import ru.itmo.eduassistant.commons.dto.queue.AllStudentInQueueResponse;
import ru.itmo.eduassistant.commons.dto.queue.AllStudentQueuesResponse;
import ru.itmo.eduassistant.commons.dto.queue.AllTeacherQueuesResponse;
import ru.itmo.eduassistant.commons.dto.queue.QueueResponse;
import ru.itmo.eduassistant.commons.dto.user.NextUserResponse;
import ru.itmo.eduassistant.commons.dto.user.UserResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public class EduAssistantClient extends AbstractControllerHttpClient {
    public EduAssistantClient(RestTemplate restTemplate, String serviceUri) {
        super(restTemplate, serviceUri);
    }

    // User
    public UserResponse getUser(Long telegramId) {
        return this.get("/user/" + telegramId, UserResponse.class, Map.of());
    }

    // Dialog
    public QuestionResponse createQuestion(Long studentId, Long channelId, String text) {
        return this.post("/dialog/question", QuestionResponse.class,
                Map.of(), new NewQuestionRequest(studentId, channelId, text));
    }

    public AllDialogsResponse getDialogs(Long userId) {
        return this.get("/dialog", AllDialogsResponse.class, Map.of("userId", userId));
    }

    public AllMessagesResponse getDialog(Long dialogId) {
        return this.get(String.format("/dialog/%s", dialogId), AllMessagesResponse.class, Map.of());
    }

    public MessageResponse addMessageToDialog(Long userId, String text, Long dialogId) {
        return this.post("/dialog", MessageResponse.class, Map.of(), new NewMessageRequest(userId, text, dialogId));
    }

    public void markAsDiscussed(Long dialogId) {
        this.post("/dialog/close", void.class, Map.of("dialogId", dialogId), null);
    }

    // Channel
    public ChannelResponse createChannel(Long teacherId, String name) {
        return this.post("/channel", ChannelResponse.class, Map.of(), new CreateChannelRequest(teacherId, name));
    }

    public void addUserToChannel(Long channelId, Long telegramId) {
        this.post(String.format("/channel/%s/user/%s", channelId, telegramId), void.class, Map.of(), null);
    }

    public void deleteUserFromChannel(Long channelId, Long telegramId) {
        this.delete(String.format("/channel/%s/user/%s", channelId, telegramId), Map.of(), null);
    }

    public AllChannelsResponse getStudentChannels(Long studentId) {
        return this.get("/channel", AllChannelsResponse.class, Map.of("studentId", studentId));
    }

    public AllChannelsResponse getTeacherChannels(Long teacherId) {
        return this.get("/channel/teacher/" + teacherId, AllChannelsResponse.class, Map.of());
    }

    public ChannelResponse getChannel(Long channelId) {
        return this.get("/channel/" + channelId, ChannelResponse.class, Map.of());
    }

    public AllNotificationsResponse getChannelNotifications(Long channel) {
        return this.post(String.format("/channel/%s/notifications", channel),
                AllNotificationsResponse.class, Map.of(), null);
    }

    public NotificationResponse createNotification(Long channelId, String text) {
        return this.post("/notifications", NotificationResponse.class, Map.of(),
                new CreateNotificationRequest(channelId, text));
    }

    public List<NotificationResponse> getAllNotifications(Long telegramId) {
        return this.get("/notifications", new ParameterizedTypeReference<>() {
        }, Map.of("telegramId", telegramId));
    }

    public AllStudentsInChannelResponse getChannelSubscribers(Long channelId) {
        return this.get("/channel/%s/students".formatted(channelId), AllStudentsInChannelResponse.class, Map.of());
    }


    // Queue
    public Long createQueue(Long channelId, String name, LocalDateTime expirationDate) {
        return this.post("/queue", Long.class,
                Map.of("channelId", channelId, "name", name, "expirationDate", expirationDate), null);
    }

    public void deleteQueue(Long id) {
        this.delete("/queue/" + id, Map.of(), null);
    }

    public QueueResponse getQueue(Long id) {
        return this.get("/queue/" + id, QueueResponse.class, Map.of());
    }

    public AllStudentQueuesResponse getStudentQueues(Long studentId) {
        return this.get("/queue/student/" + studentId, AllStudentQueuesResponse.class, Map.of());
    }

    public AllTeacherQueuesResponse getTeacherQueues(Long teacherId) {
        return this.get("/queue/teacher/" + teacherId, AllTeacherQueuesResponse.class, Map.of());
    }

    public void addNewStudentToQueue(Long queueId, Long studentId) {
        this.post(String.format("/queue/%s/students", queueId), void.class, Map.of("studentId", studentId), null);
    }

    public void deleteStudentFromQueue(Long queueId, Long studentId) {
        this.delete(String.format("/queue/%s/students/%s", queueId, studentId), Map.of(), null);
    }

    public AllStudentInQueueResponse getAllStudentsInQueue(Long queueId) {
        return this.get("/queue/%s/students".formatted(queueId), AllStudentInQueueResponse.class, Map.of());
    }

    public NextUserResponse getCurrentStudentInQueue(Long queueId) {
        return this.get("/queue/%s/student/next".formatted(queueId), NextUserResponse.class, Map.of());
    }
}
