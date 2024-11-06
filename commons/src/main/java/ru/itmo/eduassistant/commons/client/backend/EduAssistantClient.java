package ru.itmo.eduassistant.commons.client.backend;

import org.springframework.web.client.RestTemplate;
import ru.itmo.eduassistant.commons.client.AbstractControllerHttpClient;
import ru.itmo.eduassistant.commons.dto.notification.AllNotificationsResponse;
import ru.itmo.eduassistant.commons.dto.notofication.NotificationStatus;
import ru.itmo.eduassistant.commons.dto.question.AllQuestionsResponse;
import ru.itmo.eduassistant.commons.dto.queue.AllStudentQueuesResponse;
import ru.itmo.eduassistant.commons.dto.queue.AllTeacherQueuesResponse;
import ru.itmo.eduassistant.commons.dto.queue.QueueResponse;
import ru.itmo.eduassistant.commons.dto.channel.AllChannelsResponse;
import ru.itmo.eduassistant.commons.dto.channel.QuestionRequest;
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

    // Subject
    public AllChannelsResponse getStudentSubjects(Long studentId) {
        return this.get("/subject", AllChannelsResponse.class, Map.of("studentId", studentId));
    }

    public AllChannelsResponse getTeacherSubjects(Long teacherId) {
        return this.get("/subject/teacher/" + teacherId, AllChannelsResponse.class, Map.of());
    }

    public ChannelResponse getSubject(Long subjectId) {
        return this.get("/subject/" + subjectId, ChannelResponse.class, Map.of());
    }

    public AllNotificationsResponse getSubjectNotifications(Long subjectId, NotificationStatus status) {
        return this.post(String.format("/subject/%s/notifications", subjectId), AllNotificationsResponse.class, Map.of("notificationStatus", status), null);
    }

    public void createQuestion(Long userId, Long subjectId, String text) {
        this.post(String.format("/subject/%s/questions", subjectId), Long.class, Map.of(), new QuestionRequest(userId, text));
    }

    public AllQuestionsResponse getAllQuestions(Long subjectId) {
        return this.get(String.format("/subject/%s/questions", subjectId), AllQuestionsResponse.class, Map.of());
    }

    public void markAsDiscussed(Long subjectId, Long questionId) {
        this.post(String.format("/subject/%s/questions/%s", subjectId, questionId), void.class, Map.of(), null);
    }

    // Queue
    public Long createQueue(Long subjectId, String name, LocalDateTime expirationDate) {
        return this.post("/queue", Long.class,
                Map.of("subjectId", subjectId, "name", name, "expirationDate", expirationDate), null);
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
