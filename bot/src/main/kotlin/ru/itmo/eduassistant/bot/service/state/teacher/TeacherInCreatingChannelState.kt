package ru.itmo.eduassistant.bot.service.state.teacher

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import ru.itmo.eduassistant.bot.annotation.BotState
import ru.itmo.eduassistant.bot.cache.DataCache
import ru.itmo.eduassistant.bot.enm.Command
import ru.itmo.eduassistant.bot.enm.DataType
import ru.itmo.eduassistant.bot.service.ChatService
import ru.itmo.eduassistant.bot.service.StateSwitcher
import ru.itmo.eduassistant.bot.service.state.State
import ru.itmo.eduassistant.bot.util.KeyboardCreator
import ru.itmo.eduassistant.bot.util.MessageUtils
import ru.itmo.eduassistant.commons.client.backend.EduAssistantClient

@BotState
class TeacherInCreatingChannelState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    private val eduAssistantClient: EduAssistantClient,
    private val chatService: ChatService,
    private val dataCache: DataCache
) : State(stateSwitcher, keyboardCreator, messageUtils, mapOf(Command.BACK to TeacherInChannelMenuState::class)) {

    override fun initState(chatId: Long): BotApiMethod<*> {
        val sendMessage = SendMessage(chatId.toString(), "Введите название канала")
        sendMessage.replyMarkup = keyboard
        return sendMessage
    }

    override fun handleMessage(message: Message): BotApiMethod<*> {
        val command = Command.parseCommand(message.text)
        if (Command.START == command || Command.BACK == command) {
            return super.handleMessage(message)
        }

        val chatId = message.chatId
        val userId = chatService.getUserIdByChatId(chatId)
        val name = message.text
        val channelId = eduAssistantClient.createChannel(userId, name).id
        messageUtils.sendMessage(SendMessage(chatId.toString(), "Канал успешно создан!"))
        dataCache.putInputDataValue(chatId, DataType.TEACHER_IN_CHANNEL_VIEW_STATE_CURRENT_CHANNEL_ID, channelId)
        return stateSwitcher.switchState(
            chatId,
            TeacherInChannelViewState::class
        )
    }
}