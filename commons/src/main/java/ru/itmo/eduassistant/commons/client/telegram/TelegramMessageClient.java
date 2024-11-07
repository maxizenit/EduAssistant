package ru.itmo.eduassistant.commons.client.telegram;

import org.springframework.web.client.RestTemplate;
import ru.itmo.eduassistant.commons.client.AbstractControllerHttpClient;
import ru.itmo.eduassistant.commons.dto.telegram.SendNotificationRequest;

public class TelegramMessageClient extends AbstractControllerHttpClient {

    public TelegramMessageClient(RestTemplate restTemplate, String serviceUri) {
        super(restTemplate, serviceUri);
    }

    public void postNotifications(SendNotificationRequest message) {
        this.post("/messages", Object.class, null, message);
    }
}
