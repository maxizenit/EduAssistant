package ru.itmo.eduassistant.bot.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import ru.itmo.eduassistant.commons.client.backend.EduAssistantClient

@Configuration
class ApiConfig(private val restTemplate: RestTemplate, @Value("\${bot.backend-url}") private val backendUrl: String) {

    @Bean
    fun eduAssistantClient(): EduAssistantClient {
        return EduAssistantClient(restTemplate, backendUrl);
    }
}