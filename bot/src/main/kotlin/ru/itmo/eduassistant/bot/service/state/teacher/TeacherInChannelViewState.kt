package ru.itmo.eduassistant.bot.service.state.teacher

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
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
class TeacherInChannelViewState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    private val dataCache: DataCache,
    private val eduAssistantClient: EduAssistantClient,
    private val callbackUtils: CallbackUtils
) : State(
    stateSwitcher, keyboardCreator, messageUtils, mapOf(
        Command.CREATE_NOTIFICATION to TeacherInChannelViewState::class,//todo
        Command.BACK to TeacherInChannelMenuState::class
    )
) {
    private final val currentChannelIdDataType = DataType.TEACHER_IN_CHANNEL_VIEW_STATE_CURRENT_CHANNEL_ID

    override fun initState(chatId: Long): BotApiMethod<*> {
        val channelId = dataCache.getInputDataValue(chatId, currentChannelIdDataType) as Long?
            ?: return stateSwitcher.switchToLongTimeInactiveState(chatId)
        val channel = eduAssistantClient.getChannel(channelId)
        val messageText = "Название: ${channel.name}\n" +
                "id: ${channel.id}"
        val message = SendMessage(chatId.toString(), messageText)
        message.replyMarkup = keyboard
        return message
    }

    override fun handleCallback(callback: CallbackQuery): BotApiMethod<*> {
        val callbackData = callbackUtils.parseCallbackData(callback.data)
        val chatId = callbackData.chatId
        val channelId = callbackData.data.toLong()
        dataCache.putInputDataValue(chatId, currentChannelIdDataType, channelId)
        return initState(chatId)
    }
}