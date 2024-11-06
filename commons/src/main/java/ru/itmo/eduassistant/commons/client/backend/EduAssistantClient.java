package ru.itmo.eduassistant.commons.client.backend;

import org.springframework.web.client.RestTemplate;
import ru.itmo.eduassistant.commons.client.AbstractControllerHttpClient;
import ru.itmo.eduassistant.commons.dto.dialog.*;
import ru.itmo.eduassistant.commons.dto.notification.AllNotificationsResponse;
import ru.itmo.eduassistant.commons.dto.notofication.NotificationStatus;
import ru.itmo.eduassistant.commons.dto.queue.AllStudentQueuesResponse;
import ru.itmo.eduassistant.commons.dto.queue.AllTeacherQueuesResponse;
import ru.itmo.eduassistant.commons.dto.queue.QueueResponse;
import ru.itmo.eduassistant.commons.dto.channel.AllChannelsResponse;
import ru.itmo.eduassistant.commons.dto.channel.ChannelResponse;
import ru.itmo.eduassistant.commons.dto.user.UserResponse;

import java.time.LocalDateTime;
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

    public MessageResponse addMessageToDialog(Long userId, String text, Long dialogId){
        return this.post("/dialog", MessageResponse.class, Map.of(), new NewMessageRequest(userId, text, dialogId));
    }

    public void markAsDiscussed(Long dialogId) {
        this.post("/dialog/close", void.class, Map.of("dialogId", dialogId), null);
    }

    // Channel
    public AllChannelsResponse getStudentChannels(Long studentId) {
        return this.get("/channel", AllChannelsResponse.class, Map.of("studentId", studentId));
    }

    public AllChannelsResponse getTeacherChannels(Long teacherId) {
        return this.get("/channel/teacher/" + teacherId, AllChannelsResponse.class, Map.of());
    }
    public ChannelResponse getChannel(Long channelId) {
        return this.get("/channel/" + channelId, ChannelResponse.class, Map.of());
    }
//    TODO uncomment when implemented
//    public Long createChannel(Long teacherId, String title) {
//        return this.post("/channel", Long.class, Map.of("teacherId", teacherId, "title", title), null);
//    }
    public AllNotificationsResponse getChannelNotifications(Long channel, NotificationStatus status) {
        return this.post(String.format("/channel/%s/notifications", channel),
                AllNotificationsResponse.class, Map.of("status", status), null);
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
}
