package ru.itmo.eduassistant.bot.service.state.common

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import ru.itmo.eduassistant.bot.annotation.BotState
import ru.itmo.eduassistant.bot.enm.Command
import ru.itmo.eduassistant.bot.service.ChatService
import ru.itmo.eduassistant.bot.service.StateSwitcher
import ru.itmo.eduassistant.bot.service.state.State
import ru.itmo.eduassistant.bot.service.state.student.StudentInMainMenuState
import ru.itmo.eduassistant.bot.service.state.teacher.TeacherInMainMenuState
import ru.itmo.eduassistant.bot.util.KeyboardCreator
import ru.itmo.eduassistant.bot.util.MessageUtils
import ru.itmo.eduassistant.commons.client.backend.EduAssistantClient
import ru.itmo.eduassistant.commons.dto.user.UserRole

@BotState
class UnauthorizedState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    private val chatService: ChatService,
    private val eduAssistantClient: EduAssistantClient
) : State(
    stateSwitcher, keyboardCreator, messageUtils, mapOf(Command.UPDATE to UnauthorizedState::class)
) {
    override fun initState(chatId: Long): BotApiMethod<*> {
        val userId = chatService.getUserIdByChatId(chatId)
        chatService.saveChatUserBind(chatId, userId)

        try {
            val user = eduAssistantClient.getUser(userId)
            if (user.role != null) {
                return stateSwitcher.switchState(
                    chatId, when (user.role) {
                        UserRole.STUDENT -> StudentInMainMenuState::class
                        UserRole.TEACHER -> TeacherInMainMenuState::class
                    }
                )
            }
        } catch (_: Exception) {
        }

        val sendMessage = SendMessage(
            chatId.toString(),
            "Вы не авторизованы! Обратитесь к администратору для добавления в систему и нажмите \"Обновить\" после добавления"
        )
        sendMessage.replyMarkup = keyboard
        return sendMessage
    }

    override fun handleMessage(message: Message): BotApiMethod<*> {
        return initState(message.chatId)
    }

    override fun handleCallback(callback: CallbackQuery): BotApiMethod<*> {
        throw RuntimeException()
    }
}