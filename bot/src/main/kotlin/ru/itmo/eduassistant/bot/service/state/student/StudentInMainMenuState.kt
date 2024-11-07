package ru.itmo.eduassistant.bot.service.state.student

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
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
class StudentInMainMenuState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    private val chatService: ChatService,
    private val eduAssistantClient: EduAssistantClient
) : State(
    stateSwitcher, keyboardCreator, messageUtils, mapOf(
        Command.NOTIFICATIONS to StudentInMainMenuState::class,//todo
        Command.MY_CHANNELS to StudentInChannelMenuState::class,
        Command.MY_QUESTIONS to StudentInMainMenuState::class,//todo
        Command.QUEUE to StudentInMainMenuState::class
    )
) {
    override fun handleMessage(message: Message): BotApiMethod<*> {
        val command = Command.parseCommand(message.text)
        when (command) {
            Command.NOTIFICATIONS -> return handleNotifications(message)
            Command.QUEUE -> return handleQueues(message)
            else -> {}
        }
        return super.handleMessage(message)
    }

    //todo: предусмотреть клавиатуру с вперёд-назад
    private fun handleNotifications(message: Message): BotApiMethod<*> {
        val chatId = message.chatId
        val userId = chatService.getChatIdByUserId(chatId)

        //val initMessage = SendMessage(chatId.toString(), "Выберите вопрос")
        //initMessage.replyMarkup = keyboard
        //messageSender.sendMessage(initMessage)

        val inlineKeyboard =
            keyboardCreator.createInlineKeyboardMarkup(
                emptyMap(),//todo: список уведомлений
                CallbackType.STUDENT_IN_MAIN_MENU_STATE_QUEUE_CHOICE, userId//todo: другой колбэк (вперёд-назад)
            )
        val subjectsMessage = SendMessage(chatId.toString(), "Выберите очередь:")
        subjectsMessage.replyMarkup = inlineKeyboard
        return subjectsMessage
    }

    private fun handleQueues(message: Message): BotApiMethod<*> {
        val chatId = message.chatId
        val userId = chatService.getChatIdByUserId(chatId)

        val queues = try {
            eduAssistantClient.getTeacherQueues(userId).queues
        } catch (_: Exception) {
            null
        }

        val textDataMap = queues?.associateBy({ "${it.channelName}: ${it.name}" }, { it.id.toString() }) ?: emptyMap()
        val inlineKeyboard = keyboardCreator.createInlineKeyboardMarkup(
            textDataMap,
            CallbackType.STUDENT_IN_MAIN_MENU_STATE_QUEUE_CHOICE, userId
        )

        val sendMessage = SendMessage(chatId.toString(), "Ваши очереди:")
        sendMessage.replyMarkup = inlineKeyboard
        return sendMessage
    }
}