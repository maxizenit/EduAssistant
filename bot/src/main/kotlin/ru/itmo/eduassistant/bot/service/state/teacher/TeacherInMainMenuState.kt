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

    //todo: предусмотреть клавиатуру с вперёд-назад
    private fun handleNotifications(message: Message): BotApiMethod<*> {
        val chatId = message.chatId
        val userId = chatService.getChatIdByUserId(chatId)

        //val initMessage = SendMessage(chatId.toString(), "Выберите вопрос")
        //initMessage.replyMarkup = keyboard
        //messageSender.sendMessage(initMessage)

        val inlineKeyboard =
            keyboardCreator.createInlineKeyboardMarkup(
                emptyMap(),//todo: список очередей
                CallbackType.STUDENT_IN_MAIN_MENU_STATE_QUEUE_CHOICE, userId//todo: другой колбэк
            )
        val subjectsMessage = SendMessage(chatId.toString(), "Выберите очередь:")
        subjectsMessage.replyMarkup = inlineKeyboard
        return subjectsMessage
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
        val answer = SendMessage(chatId.toString(), "Вопросы к Вам:")
        answer.replyMarkup = inlineKeyboard
        return answer
    }
}