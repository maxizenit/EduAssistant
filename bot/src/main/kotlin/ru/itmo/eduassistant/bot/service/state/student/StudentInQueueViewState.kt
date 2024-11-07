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
class StudentInQueueViewState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    private val callbackUtils: CallbackUtils,
    private val dataCache: DataCache,
    private val eduAssistantClient: EduAssistantClient,
) : State(
    stateSwitcher, keyboardCreator, messageUtils, mapOf(
        Command.SIGN_UP_IN_QUEUE to StudentInQueueViewState::class,
        Command.CANCEL_THE_QUEUE_ENTRY to StudentInQueueViewState::class,
        Command.BACK to StudentInMainMenuState::class
    )
) {

    private final val currentQueueIdDataType = DataType.STUDENT_IN_QUEUE_VIEW_STATE_CURRENT_QUEUE_ID

    override fun initState(chatId: Long): BotApiMethod<*> {
        val queueId = dataCache.getInputDataValue(chatId, currentQueueIdDataType) as Long?
            ?: return stateSwitcher.switchToLongTimeInactiveState(chatId)
        val queue = eduAssistantClient.getQueue(queueId)
        val studentsInQueue = eduAssistantClient.getAllStudentsInQueue(queueId).allStudents.map { it.fio }
        var counter = 0
        val messageText = "\uD83D\uDCAC *Канал:* ${queue.channelName}\n" +
                "*Название очереди:* ${queue.name}\n\n" +
                "_Список участников:_\n" +
                studentsInQueue.joinToString(separator = "\n") { (++counter).toString() + ". " + it }
        val message = SendMessage(chatId.toString(), messageText)
        message.enableMarkdown(true)
        message.replyMarkup = keyboard
        return message
    }

    override fun handleMessage(message: Message): BotApiMethod<*> {
        val command = Command.parseCommand(message.text)
        if (Command.SIGN_UP_IN_QUEUE != command && Command.CANCEL_THE_QUEUE_ENTRY != command) {
            return super.handleMessage(message)
        }

        val chatId = message.chatId
        val userId = message.from.id
        val queueId = dataCache.getInputDataValue(chatId, currentQueueIdDataType) as Long?
            ?: return stateSwitcher.switchToLongTimeInactiveState(chatId)

        when (command) {
            Command.SIGN_UP_IN_QUEUE -> {
                try {
                    eduAssistantClient.addNewStudentToQueue(queueId, userId)
                    messageUtils.sendMessage(SendMessage(chatId.toString(), "Вы записаны в очередь"))
                } catch (_: Exception) {
                    return SendMessage(chatId.toString(), "Ошибка записи в очередь")
                }
            }

            Command.CANCEL_THE_QUEUE_ENTRY -> {
                try {
                    eduAssistantClient.deleteStudentFromQueue(queueId, userId)
                    messageUtils.sendMessage(SendMessage(chatId.toString(), "Вы больше не записаны в очередь"))
                } catch (_: Exception) {
                    return SendMessage(chatId.toString(), "Ошибка отмены записи в очередь")
                }
            }

            else -> throw RuntimeException()
        }

        return initState(chatId)
    }

    override fun handleCallback(callback: CallbackQuery): BotApiMethod<*> {
        val callbackData = callbackUtils.parseCallbackData(callback.data)
        val chatId = callbackData.chatId
        val queueId = callbackData.data.toLong()
        dataCache.putInputDataValue(chatId, currentQueueIdDataType, queueId)
        return initState(chatId)
    }
}