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

@BotState
class TeacherInCreatingQuestionChainMessageState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    private val dataCache: DataCache
) :
    State(stateSwitcher, keyboardCreator, messageUtils, mapOf(Command.BACK to TeacherInQuestionViewState::class)) {

    private final val currentQuestionIdDataType =
        DataType.TEACHER_IN_CREATING_QUESTION_CHAIN_MESSAGE_STATE_CURRENT_QUESTION_ID

    override fun initState(chatId: Long): BotApiMethod<*> {
        val sendMessage = SendMessage(chatId.toString(), "Введите новое сообщение")
        sendMessage.replyMarkup = keyboard
        return sendMessage
    }

    override fun handleMessage(message: Message): BotApiMethod<*> {
        val chatId = message.chatId
        val currentQuestionId = dataCache.getInputDataValue(chatId, currentQuestionIdDataType) as Long?
            ?: return stateSwitcher.switchToLongTimeInactiveState(chatId)

        val newQuestionMessage = message.text
        //todo: сохранение нового вопроса в цепочке в бэкенде
        messageUtils.sendMessage(SendMessage(chatId.toString(), "Новое сообщение успешно отправлено!"))
        return stateSwitcher.switchState(chatId, TeacherInQuestionViewState::class)
    }
}