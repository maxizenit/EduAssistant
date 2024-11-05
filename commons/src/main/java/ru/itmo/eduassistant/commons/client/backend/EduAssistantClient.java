package ru.itmo.eduassistant.commons.client.backend;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;
import ru.itmo.eduassistant.commons.client.AbstractControllerHttpClient;
import ru.itmo.eduassistant.commons.client.backend.dto.Dialog;
import ru.itmo.eduassistant.commons.client.backend.dto.Event;
import ru.itmo.eduassistant.commons.client.backend.dto.Message;

import java.util.List;
import java.util.Map;

public class EduAssistantClient extends AbstractControllerHttpClient {
    public EduAssistantClient(RestTemplate restTemplate, String serviceUri) {
        super(restTemplate, serviceUri);
    }

    public List<Event> getEvents(Long userId, Long subjectId) {
        return this.get("/events", new ParameterizedTypeReference<>() {
        }, Map.of("user_id", userId, "subject_id", subjectId));
    }

    public List<Message> getMessages(Long userId, Long dialogId) {
        return this.get("/messages", new ParameterizedTypeReference<>() {
        }, Map.of("user_id", userId, "dialog_id", dialogId));
    }

    public List<Dialog> getDialogs(Long userId, Long subjectId) {
        return this.get("/dialogs", new ParameterizedTypeReference<>() {
        }, Map.of("user_id", userId, "subject_id", subjectId));
    }

    public void postQuestion(Long userId, Long subjectId) {
        this.post("/question", void.class, Map.of("user_id", userId, "subject_id", subjectId), null);
    }
}

