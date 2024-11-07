package ru.itmo.eduassistant.bot.service

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class EduAssistantBot(
    @Value("\${bot.username}") private val botUsername: String,
    @Value("\${bot.token}") botToken: String,
    private val setWebhook: SetWebhook
) : TelegramWebhookBot(botToken) {

    @PostConstruct
    fun init() {
        setWebhook(setWebhook)
    }

    override fun getBotPath(): String {
        return "/update"
    }

    override fun getBotUsername(): String {
        return botUsername
    }

    override fun onWebhookUpdateReceived(update: Update?): BotApiMethod<*>? {
        return null
    }
}