package ru.itmo.eduassistant.bot.util

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import ru.itmo.eduassistant.bot.service.EduAssistantBot

@Service
class MessageUtils(private val eduAssistantBot: EduAssistantBot) {

    fun sendMessage(message: SendMessage) {
        eduAssistantBot.execute(message)
    }

    fun deleteMessage(chatId: Long, messageId: Int) {
        eduAssistantBot.execute(DeleteMessage(chatId.toString(), messageId))
    }
}