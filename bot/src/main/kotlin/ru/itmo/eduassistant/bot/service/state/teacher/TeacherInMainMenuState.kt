package ru.itmo.eduassistant.bot.service.state.teacher

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
class TeacherInMainMenuState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    //private val messageSender: MessageSender,
    private val chatService: ChatService,
    private val eduAssistantClient: EduAssistantClient
) : State(
    stateSwitcher, keyboardCreator, messageUtils, mapOf(
        Command.NOTIFICATIONS to TeacherInMainMenuState::class,
        Command.MY_CHANNELS to TeacherInChannelMenuState::class,
        Command.MY_QUESTIONS to TeacherInMainMenuState::class,
        Command.QUEUE to TeacherInQueueMenuState::class
    )
) {
    override fun handleMessage(message: Message): BotApiMethod<*> {
        val command = Command.parseCommand(message.text)
        when (command) {
            Command.NOTIFICATIONS -> return handleNotifications(message)
            Command.MY_QUESTIONS -> return handleQuestions(message)
            else -> {}
        }
        return super.handleMessage(message)
    }

    private fun handleNotifications(message: Message): BotApiMethod<*> {
        val chatId = message.chatId
        val userId = chatService.getChatIdByUserId(chatId)

        val notifications = try {
            eduAssistantClient.getAllNotifications(userId).map {"_" + it.channelName + ":_\n " + it.text }
                .joinToString(separator = "\n----------\n")
        } catch (_: Exception) {
            null
        }

        val messageText = if (notifications == null) {
            "У вас нет уведомлений"
        } else {
            "\uD83D\uDD09 *Ваши уведомления*:\n$notifications"
        }
        val answer = SendMessage(chatId.toString(), messageText)
        answer.enableMarkdown(true)
        answer.replyMarkup = keyboard
        return answer
    }

    private fun handleQuestions(message: Message): BotApiMethod<*> {
        val chatId = message.chatId
        val userId = chatService.getChatIdByUserId(chatId)

        val questions = try {
            eduAssistantClient.getDialogs(userId).questionResponses
        } catch (_: Exception) {
            null
        }

        val textDataMap = questions?.associateBy({ it.text }, { it.dialogId.toString() }) ?: emptyMap()
        val inlineKeyboard =
            keyboardCreator.createInlineKeyboardMarkup(
                textDataMap,
                CallbackType.TEACHER_IN_MAIN_MENU_STATE_QUESTION_CHOICE, userId
            )
        val answer = SendMessage(chatId.toString(), "Ваши диалоги:")
        answer.replyMarkup = inlineKeyboard
        return answer
    }
}