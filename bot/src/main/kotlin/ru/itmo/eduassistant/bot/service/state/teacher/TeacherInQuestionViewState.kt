package ru.itmo.eduassistant.bot.service.state.teacher

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import ru.itmo.eduassistant.bot.annotation.BotState
import ru.itmo.eduassistant.bot.cache.DataCache
import ru.itmo.eduassistant.bot.enm.Command
import ru.itmo.eduassistant.bot.enm.DataType
import ru.itmo.eduassistant.bot.service.StateSwitcher
import ru.itmo.eduassistant.bot.service.state.State
import ru.itmo.eduassistant.bot.util.CallbackUtils
import ru.itmo.eduassistant.bot.util.KeyboardCreator
import ru.itmo.eduassistant.bot.util.MessageUtils
import ru.itmo.eduassistant.commons.client.backend.EduAssistantClient

@BotState
class TeacherInQuestionViewState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    private val dataCache: DataCache,
    private val callbackUtils: CallbackUtils,
    private val eduAssistantClient: EduAssistantClient
) : State(
    stateSwitcher, keyboardCreator, messageUtils, mapOf(
        Command.BACK to TeacherInMainMenuState::class
    )
) {
    private final val currentQuestionIdDataType =
        DataType.TEACHER_IN_QUESTION_VIEW_STATE_CURRENT_QUESTION_ID

    override fun initState(chatId: Long): BotApiMethod<*> {
        val questionId = dataCache.getInputDataValue(chatId, currentQuestionIdDataType) as Long?
            ?: return stateSwitcher.switchToLongTimeInactiveState(chatId)
        val messages =
            eduAssistantClient.getDialog(questionId).messages.joinToString(separator = "\n") { "_" + it.authorName + "_" + it.text }
        val sendMessage = SendMessage(
            chatId.toString(), "*Сообщения в цепочке:*\n${messages}\n\n" +
                    "*Для ответа пришлите сообщение*"
        )
        sendMessage.enableMarkdown(true)
        sendMessage.replyMarkup = keyboard
        return sendMessage
    }

    override fun handleMessage(message: Message): BotApiMethod<*> {
        val command = Command.parseCommand(message.text)
        if (Command.START == command || Command.BACK == command) {
            return super.handleMessage(message)
        }
        val chatId = message.chatId
        val userId = message.from.id
        val questionId = dataCache.getInputDataValue(chatId, currentQuestionIdDataType) as Long?
            ?: return stateSwitcher.switchToLongTimeInactiveState(chatId)
        eduAssistantClient.addMessageToDialog(userId, message.text, questionId)
        return initState(chatId)
    }

    override fun handleCallback(callback: CallbackQuery): BotApiMethod<*> {
        val callbackData = callbackUtils.parseCallbackData(callback.data)
        val chatId = callbackData.chatId
        val questionId = callbackData.data.toLong()
        dataCache.putInputDataValue(chatId, currentQuestionIdDataType, questionId)
        return initState(chatId)
    }
}