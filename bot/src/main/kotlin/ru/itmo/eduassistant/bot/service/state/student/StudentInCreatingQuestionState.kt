package ru.itmo.eduassistant.bot.service.state.student

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
class StudentInCreatingQuestionState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    private val callbackUtils: CallbackUtils,
    private val dataCache: DataCache,
    private val eduAssistantClient: EduAssistantClient
) : State(stateSwitcher, keyboardCreator, messageUtils, mapOf(Command.BACK to StudentInMainMenuState::class)) {

    private final val currentChannelIdDataType = DataType.STUDENT_IN_CREATING_QUESTION_VIEW_CURRENT_CHANNEL_ID

    override fun initState(chatId: Long): BotApiMethod<*> {
        dataCache.getInputDataValue(chatId, currentChannelIdDataType)
            ?: return stateSwitcher.switchToLongTimeInactiveState(chatId)
        val sendMessage = SendMessage(chatId.toString(), "Введите вопрос")
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
        val channelId = dataCache.getInputDataValue(chatId, currentChannelIdDataType) as Long?
            ?: return stateSwitcher.switchToLongTimeInactiveState(chatId)

        eduAssistantClient.createQuestion(userId, channelId, message.text).dialogId
        messageUtils.sendMessage(SendMessage(chatId.toString(), "Вопрос отправлен!"))
        return stateSwitcher.switchState(chatId, StudentInQuestionMenuState::class)
    }

    override fun handleCallback(callback: CallbackQuery): BotApiMethod<*> {
        val callbackData = callbackUtils.parseCallbackData(callback.data)
        val chatId = callbackData.chatId
        val channelId = callbackData.data.toLong()
        dataCache.putInputDataValue(chatId, currentChannelIdDataType, channelId)
        return initState(chatId)
    }
}