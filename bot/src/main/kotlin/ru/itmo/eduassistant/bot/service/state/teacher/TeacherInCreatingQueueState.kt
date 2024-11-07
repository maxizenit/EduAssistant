package ru.itmo.eduassistant.bot.service.state.teacher

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import ru.itmo.eduassistant.bot.annotation.BotState
import ru.itmo.eduassistant.bot.cache.DataCache
import ru.itmo.eduassistant.bot.enm.CallbackType
import ru.itmo.eduassistant.bot.enm.Command
import ru.itmo.eduassistant.bot.enm.DataType
import ru.itmo.eduassistant.bot.service.ChatService
import ru.itmo.eduassistant.bot.service.StateSwitcher
import ru.itmo.eduassistant.bot.service.state.State
import ru.itmo.eduassistant.bot.util.CallbackUtils
import ru.itmo.eduassistant.bot.util.KeyboardCreator
import ru.itmo.eduassistant.bot.util.MessageUtils
import ru.itmo.eduassistant.commons.client.backend.EduAssistantClient
import java.time.LocalDateTime

@BotState
class TeacherInCreatingQueueState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    private val chatService: ChatService,
    private val dataCache: DataCache,
    private val callbackUtils: CallbackUtils,
    private val eduAssistantClient: EduAssistantClient
) : State(stateSwitcher, keyboardCreator, messageUtils, mapOf(Command.BACK to TeacherInQueueMenuState::class)) {

    private final val queueNameDataType = DataType.TEACHER_IN_CREATING_QUEUE_STATE_QUEUE_NAME

    override fun initState(chatId: Long): BotApiMethod<*> {
        val sendMessage = SendMessage(chatId.toString(), "Введите название очереди")
        sendMessage.replyMarkup = keyboard
        return sendMessage
    }

    override fun handleMessage(message: Message): BotApiMethod<*> {
        val command = Command.parseCommand(message.text)
        val chatId = message.chatId

        if (Command.START == command || Command.BACK == command) {
            dataCache.removeInputDataValue(chatId, queueNameDataType)
            return super.handleMessage(message)
        }

        val cachedName =
            dataCache.getInputDataValue(chatId, queueNameDataType) as String?
        if (cachedName != null) {
            messageUtils.sendMessage(
                SendMessage(
                    chatId.toString(),
                    "Название создаваемой очереди перезаписано"
                )
            )
        }
        dataCache.putInputDataValue(chatId, queueNameDataType, message.text)

        val initMessage = SendMessage(chatId.toString(), "Выберите канал")
        initMessage.replyMarkup = keyboard
        messageUtils.sendMessage(initMessage)

        val userId = chatService.getUserIdByChatId(chatId)
        val channels = try {
            eduAssistantClient.getTeacherChannels(userId).channels
        } catch (_: Exception) {
            null
        }

        val textDataMap = channels?.associateBy({ it.name }, { it.id.toString() }) ?: emptyMap()
        val inlineKeyboard = keyboardCreator.createInlineKeyboardMarkup(
            textDataMap,
            CallbackType.TEACHER_IN_CREATING_QUEUE_STATE_CHANNEL_CHOICE,
            userId
        )
        val channelsMessage = SendMessage(chatId.toString(), "Каналы, которые Вы ведёте:")
        channelsMessage.replyMarkup = inlineKeyboard
        return channelsMessage
    }

    override fun handleCallback(callback: CallbackQuery): BotApiMethod<*> {
        val callbackData = callbackUtils.parseCallbackData(callback.data)
        val chatId = callbackData.chatId
        val channelId = callbackData.data.toLong()

        val queueName =
            dataCache.getInputDataValue(chatId, queueNameDataType) as String?
                ?: return stateSwitcher.switchToLongTimeInactiveState(chatId)

        val queueId = eduAssistantClient.createQueue(channelId, queueName, LocalDateTime.now().plusDays(1))
        dataCache.removeInputDataValue(chatId, queueNameDataType)
        dataCache.putInputDataValue(chatId, DataType.TEACHER_IN_QUEUE_VIEW_STATE_CURRENT_QUEUE_ID, queueId)
        messageUtils.sendMessage(SendMessage(chatId.toString(), "Очередь успешно создана!"))
        return stateSwitcher.switchState(chatId, TeacherInQueueViewState::class)
    }
}