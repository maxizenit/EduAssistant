package ru.itmo.eduassistant.bot.service.state.teacher

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import ru.itmo.eduassistant.bot.annotation.BotState
import ru.itmo.eduassistant.bot.cache.DataCache
import ru.itmo.eduassistant.bot.enm.Command
import ru.itmo.eduassistant.bot.enm.DataType
import ru.itmo.eduassistant.bot.service.StateSwitcher
import ru.itmo.eduassistant.bot.service.state.State
import ru.itmo.eduassistant.bot.util.KeyboardCreator
import ru.itmo.eduassistant.bot.util.MessageUtils
import ru.itmo.eduassistant.commons.client.backend.EduAssistantClient

@BotState
class TeacherInCreatingChannelNotificationState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    private val dataCache: DataCache,
    private val eduAssistantClient: EduAssistantClient
) : State(
    stateSwitcher, keyboardCreator, messageUtils, mapOf(
        Command.BACK to TeacherInChannelViewState::class,
        Command.MAIN_MENU to TeacherInMainMenuState::class
    )
) {
    private final val currentChannelIdDataType = DataType.TEACHER_IN_CHANNEL_VIEW_STATE_CURRENT_CHANNEL_ID

    override fun initState(chatId: Long): BotApiMethod<*> {
        dataCache.getInputDataValue(chatId, currentChannelIdDataType) as Long?
            ?: return stateSwitcher.switchToLongTimeInactiveState(chatId)
        val message = SendMessage(chatId.toString(), "Введите уведомление")
        message.replyMarkup = keyboard
        return message
    }

    override fun handleMessage(message: Message): BotApiMethod<*> {
        val command = Command.parseCommand(message.text)
        if (Command.START == command || Command.BACK == command || Command.MAIN_MENU == command) {
            return super.handleMessage(message)
        }
        val chatId = message.chatId
        val channelId = dataCache.getInputDataValue(chatId, currentChannelIdDataType) as Long?
            ?: return stateSwitcher.switchToLongTimeInactiveState(chatId)
        eduAssistantClient.createNotification(chatId, message.text)
        return SendMessage(chatId.toString(), "Уведомление создано!")
    }
}