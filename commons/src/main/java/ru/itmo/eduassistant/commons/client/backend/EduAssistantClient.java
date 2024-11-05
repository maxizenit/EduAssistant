package ru.itmo.eduassistant.commons.client.backend;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;
import ru.itmo.eduassistant.commons.client.AbstractControllerHttpClient;
import ru.itmo.eduassistant.commons.client.backend.dto.*;

import java.util.List;
import java.util.Map;

public class EduAssistantClient extends AbstractControllerHttpClient {
    public EduAssistantClient(RestTemplate restTemplate, String serviceUri) {
        super(restTemplate, serviceUri);
    }

    public List<Subject> getSubjects(Long userId) {
        return this.get("/subjects", new ParameterizedTypeReference<>() {
        }, Map.of("user_id", userId));
    }

    public List<Event> getSubjectEvents(Long userId, Long subjectId) {
        return this.get("/events", new ParameterizedTypeReference<>() {
        }, Map.of("user_id", userId, "subject_id", subjectId));
    }

    public List<Event> getUserEvents(Long userId) {
        return this.get("/events", new ParameterizedTypeReference<>() {
        }, Map.of("user_id", userId));
    }

    public Long postQuestion(Long userId, Long subjectId) {
        return this.post("/question", Long.class,
                Map.of("user_id", userId, "subject_id", subjectId), null);
    }

    public List<Message> getMessages(Long userId, Long dialogId) {
        return this.get("/messages", new ParameterizedTypeReference<>() {
        }, Map.of("user_id", userId, "dialog_id", dialogId));
    }

    public List<Dialog> getDialogs(Long userId, Long subjectId) {
        return this.get("/dialogs", new ParameterizedTypeReference<>() {
        }, Map.of("user_id", userId, "subject_id", subjectId));
    }

    public Dialog getDialog(Long userId, Long dialogId) {
        return this.get("/dialogs" + dialogId, new ParameterizedTypeReference<>() {
        }, Map.of("user_id", userId));
    }

    // Queues
    public void createQueue(Long subjectId, Long groupId) {

        this.put("/queues", void.class, Map.of(), null);
    }

    public Queue getQueue(Long id) {
        return this.get("/queue/" + id, Queue.class, Map.of());
    }

    public void deleteQueue(Long id) {
        this.delete("/queues/" + id, Map.of(), null);
    }

    public List<Queue> getQueues(Long userId) {
        return this.get("/queues", new ParameterizedTypeReference<>() {
        }, Map.of("user_id", userId));
    }

    public void applyToQueue(Long id, Long userId) {
        this.post("/queues/" + id, void.class, Map.of("user_id", userId), null);
    }
}
