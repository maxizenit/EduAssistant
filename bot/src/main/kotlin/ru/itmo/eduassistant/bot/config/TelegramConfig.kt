package ru.itmo.eduassistant.bot.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook

@Configuration
class TelegramConfig {

    @Bean
    fun setWebhook(@Value("\${bot.webhook-path}") webhookPath: String): SetWebhook {
        return SetWebhook(webhookPath)
    }
}