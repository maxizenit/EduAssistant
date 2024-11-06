package ru.itmo.eduassistant.commons.client.backend;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;
import ru.itmo.eduassistant.commons.client.AbstractControllerHttpClient;
import ru.itmo.eduassistant.commons.client.backend.dto.*;
import ru.itmo.eduassistant.commons.dto.notofication.AllNotificationsResponse;
import ru.itmo.eduassistant.commons.dto.notofication.NotificationStatus;
import ru.itmo.eduassistant.commons.dto.queue.StudentRequest;
import ru.itmo.eduassistant.commons.dto.subject.QuestionRequest;
import ru.itmo.eduassistant.commons.dto.subject.SubjectResponse;

import java.util.List;
import java.util.Map;

public class EduAssistantClient extends AbstractControllerHttpClient {
    public EduAssistantClient(RestTemplate restTemplate, String serviceUri) {
        super(restTemplate, serviceUri);
    }

    //Subject
    public List<Subject> getSubjects(Long userId) {
        return this.get("/subject", new ParameterizedTypeReference<>() {
        }, Map.of("student_id", userId));
    }

    public SubjectResponse getSubject(Long subjectId) {
        return this.get("/subject/" + subjectId, SubjectResponse.class, Map.of());
    }

    public AllNotificationsResponse getSubjectNotifications(Long subjectId, NotificationStatus status) {
        return this.post(String.format("/subject/%s/notifications", subjectId), AllNotificationsResponse.class,
                Map.of("notification_status", status), null);
    }
    public Long createQuestion(Long userId, Long subjectId, String text) {
        return this.post(String.format("/subject/%s/questions",subjectId), Long.class,
                Map.of(), new QuestionRequest(userId, text));
    }

    public List<QuestionRequest> getQuestions(Long userId, Long subjectId) {
        return this.get(String.format("/subject/%s/questions", subjectId), new ParameterizedTypeReference<>() {
        }, Map.of());
    }

    public void markAsDiscussed(Long subjectId, Long questionId){
        this.post(String.format("/subject/%s/questions/%s", subjectId, questionId),
                void.class, Map.of(), null);
    }

    // Queues
    public void createQueue(Long subjectId) {
        this.put("/queue", void.class, Map.of("subject_id", subjectId), null);
    }

    public void deleteQueue(Long id) {
        this.delete("/queue/" + id, Map.of(), null);
    }

    public Queue getQueue(Long id) {
        return this.get("/queue/" + id, Queue.class, Map.of());
    }

    public List<Queue> getQueues(Long userId) {
        return this.get("/queues", new ParameterizedTypeReference<>() {
        }, Map.of("user_id", userId));
    }

    public void applyToQueue(Long id, Long studentId) {
        this.post(String.format("/queue/%s/students", id), void.class, Map.of(), new StudentRequest(studentId));
    }

    public void deleteStudent(Long id, Long studentId) {
        this.delete(String.format("/queue/%s/students/%s", id, studentId), Map.of(), null);
    }
}
