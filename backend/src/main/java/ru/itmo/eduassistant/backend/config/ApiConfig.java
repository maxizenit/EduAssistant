package ru.itmo.eduassistant.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.itmo.eduassistant.commons.client.telegram.TelegramMessageClient;

@Configuration
@RequiredArgsConstructor
public class ApiConfig {
    private final RestTemplate restTemplate;
    @Value("${backend.bot-url}")
    private String telegramUrl;

    @Bean
    TelegramMessageClient telegramClient() {
        return new TelegramMessageClient(restTemplate, telegramUrl);
    }
}
