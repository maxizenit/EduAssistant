package ru.itmo.eduassistant.bot.service.state.teacher

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.itmo.eduassistant.bot.annotation.BotState
import ru.itmo.eduassistant.bot.enm.CallbackType
import ru.itmo.eduassistant.bot.enm.Command
import ru.itmo.eduassistant.bot.service.ChatService
import ru.itmo.eduassistant.bot.service.StateSwitcher
import ru.itmo.eduassistant.bot.service.state.State
import ru.itmo.eduassistant.bot.util.KeyboardCreator
import ru.itmo.eduassistant.bot.util.MessageUtils
import ru.itmo.eduassistant.commons.client.backend.EduAssistantClient

@BotState
class TeacherInQueueMenuState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    private val chatService: ChatService,
    private val eduAssistantClient: EduAssistantClient
) : State(
    stateSwitcher, keyboardCreator, messageUtils, mapOf(
        Command.CREATE_QUEUE to TeacherInCreatingQueueState::class,
        Command.BACK to TeacherInMainMenuState::class
    )
) {
    override fun initState(chatId: Long): BotApiMethod<*> {
        val sendMessage = SendMessage(chatId.toString(), "Выберите очередь")
        sendMessage.replyMarkup = keyboard
        messageUtils.sendMessage(sendMessage)
        return createQueuesListMessage(chatId)
    }

    private fun createQueuesListMessage(chatId: Long): BotApiMethod<*> {
        val userId = chatService.getUserIdByChatId(chatId)
        val queues = try {
            eduAssistantClient.getTeacherQueues(userId).queues
        } catch (_: Exception) {
            null
        }

        val textDataMap = queues?.associateBy({ "${it.channelName}: ${it.name}" }, { it.id.toString() }) ?: emptyMap()
        val inlineKeyboard = keyboardCreator.createInlineKeyboardMarkup(
            textDataMap,
            CallbackType.TEACHER_IN_QUEUE_MENU_STATE_QUEUE_CHOICE, userId
        )

        val sendMessage = SendMessage(chatId.toString(), "Ваши очереди:")
        sendMessage.replyMarkup = inlineKeyboard
        return sendMessage
    }
}