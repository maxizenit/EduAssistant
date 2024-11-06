package ru.itmo.eduassistant.bot.service

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Message
import ru.itmo.eduassistant.bot.cache.StateCache
import ru.itmo.eduassistant.bot.enm.Command

@Service
class MessageHandler(
    private val stateSwitcher: StateSwitcher,
    private val stateCache: StateCache,
    private val chatService: ChatService
) {

    fun handleMessage(message: Message): BotApiMethod<*> {
        val chatId = message.chatId
        val userId = message.from.id
        chatService.saveChatUserBind(chatId, userId)

        val currentState = stateCache.get(chatId)
        if (Command.START.text == message.text || currentState == null) {
            return stateSwitcher.switchToStartState(chatId)
        }

        return currentState.handleMessage(message)
    }
}