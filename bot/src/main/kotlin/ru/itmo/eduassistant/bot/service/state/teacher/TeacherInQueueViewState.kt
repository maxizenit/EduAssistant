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
class TeacherInQueueViewState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    private val dataCache: DataCache,
    private val eduAssistantClient: EduAssistantClient,
    private val callbackUtils: CallbackUtils
) : State(
    stateSwitcher, keyboardCreator, messageUtils, mapOf(
        Command.NEXT_QUEUE_PARTICIPANT to TeacherInQueueViewState::class,
        Command.BACK to TeacherInQueueMenuState::class,
        Command.MAIN_MENU to TeacherInMainMenuState::class
    )
) {
    private final val currentQueueIdDataType = DataType.TEACHER_IN_QUEUE_VIEW_STATE_CURRENT_QUEUE_ID

    override fun initState(chatId: Long): BotApiMethod<*> {
        val queueId =
            dataCache.getInputDataValue(chatId, DataType.TEACHER_IN_QUEUE_VIEW_STATE_CURRENT_QUEUE_ID) as Long?
                ?: return stateSwitcher.switchToLongTimeInactiveState(chatId)
        val queue = eduAssistantClient.getQueue(queueId)
        val studentsInQueue = eduAssistantClient.getAllStudentsInQueue(queueId).allStudents.map { it.fio }
        val message = "\uD83D\uDCAC *Канал:* ${queue.channelName}\n" +
                "*Название очереди:* ${queue.name}\n\n" +
                "_Список участников:_" +
                studentsInQueue.joinToString(separator = "\n")
        val sendMessage = SendMessage(chatId.toString(), message)
        sendMessage.enableMarkdown(true)
        sendMessage.replyMarkup = keyboard
        return sendMessage
    }

    override fun handleMessage(message: Message): BotApiMethod<*> {
        val command = Command.parseCommand(message.text)
        if (Command.NEXT_QUEUE_PARTICIPANT != command) {
            return super.handleMessage(message)
        }

        val chatId = message.chatId
        val queueId =
            dataCache.getInputDataValue(chatId, DataType.TEACHER_IN_QUEUE_VIEW_STATE_CURRENT_QUEUE_ID) as Long?
                ?: return stateSwitcher.switchToLongTimeInactiveState(chatId)

        try {
            eduAssistantClient.getCurrentStudentInQueue(queueId)
            return initState(chatId)
        } catch (_: Exception) {
            messageUtils.sendMessage(SendMessage(chatId.toString(), "Ошибка!"))
            return stateSwitcher.switchState(chatId, TeacherInQueueMenuState::class)
        }
    }

    override fun handleCallback(callback: CallbackQuery): BotApiMethod<*> {
        val callbackData = callbackUtils.parseCallbackData(callback.data)
        val chatId = callbackData.chatId
        val queueId = callbackData.data.toLong()
        dataCache.putInputDataValue(chatId, currentQueueIdDataType, queueId)
        return initState(chatId)
    }
}