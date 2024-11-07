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
class StudentInChannelViewState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    private val dataCache: DataCache,
    private val eduAssistantClient: EduAssistantClient,
    private val callbackUtils: CallbackUtils
) : State(
    stateSwitcher, keyboardCreator, messageUtils, mapOf(
        Command.ASK to StudentInCreatingQuestionState::class,
        Command.BACK to StudentInChannelMenuState::class
    )
) {
    private final val currentChannelIdDataType = DataType.STUDENT_IN_CHANNEL_VIEW_CURRENT_CHANNEL_ID

    override fun initState(chatId: Long): BotApiMethod<*> {
        val channelId = dataCache.getInputDataValue(chatId, currentChannelIdDataType) as Long?
            ?: return stateSwitcher.switchToLongTimeInactiveState(chatId)
        val channel = eduAssistantClient.getChannel(channelId)
        val messageText = "✏\uFE0F *Название:* ${channel.name}\n" +
                "\uD83D\uDC64 *Преподаватель:* ${channel.teacherName}\n" +
                "\uD83D\uDDD2 *Описание:* ${channel.description}\n"
        val message = SendMessage(chatId.toString(), messageText)
        message.enableMarkdown(true)
        message.replyMarkup = keyboard
        return message
    }

    override fun handleMessage(message: Message): BotApiMethod<*> {
        val command = Command.parseCommand(message.text)
        if (Command.ASK != command) {
            return super.handleMessage(message)
        }
        val chatId = message.chatId
        val channelId = dataCache.getInputDataValue(chatId, currentChannelIdDataType) as Long?
            ?: return stateSwitcher.switchToLongTimeInactiveState(chatId)
        dataCache.putInputDataValue(chatId, DataType.STUDENT_IN_CREATING_QUESTION_VIEW_CURRENT_CHANNEL_ID, channelId)
        return stateSwitcher.switchState(chatId, StudentInCreatingQuestionState::class)
    }

    override fun handleCallback(callback: CallbackQuery): BotApiMethod<*> {
        val callbackData = callbackUtils.parseCallbackData(callback.data)
        val chatId = callbackData.chatId
        val channelId = callbackData.data.toLong()
        dataCache.putInputDataValue(chatId, currentChannelIdDataType, channelId)
        return initState(chatId)
    }
}