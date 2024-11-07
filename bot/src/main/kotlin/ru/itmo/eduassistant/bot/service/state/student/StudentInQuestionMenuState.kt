package ru.itmo.eduassistant.bot.service.state.student

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import ru.itmo.eduassistant.bot.annotation.BotState
import ru.itmo.eduassistant.bot.enm.CallbackType
import ru.itmo.eduassistant.bot.enm.Command
import ru.itmo.eduassistant.bot.service.ChatService
import ru.itmo.eduassistant.bot.service.StateSwitcher
import ru.itmo.eduassistant.bot.service.state.State
import ru.itmo.eduassistant.bot.util.KeyboardCreator
import ru.itmo.eduassistant.bot.util.MessageUtils
import ru.itmo.eduassistant.commons.client.backend.EduAssistantClient

@BotState
class StudentInQuestionMenuState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    private val chatService: ChatService,
    private val eduAssistantClient: EduAssistantClient
) : State(
    stateSwitcher, keyboardCreator, messageUtils, mapOf(
        Command.ASK to StudentInCreatingQuestionState::class,
        Command.BACK to StudentInMainMenuState::class
    )
) {
    override fun initState(chatId: Long): BotApiMethod<*> {
        val userId = chatService.getChatIdByUserId(chatId)

        val questions = try {
            eduAssistantClient.getDialogs(userId).questionResponses
        } catch (_: Exception) {
            null
        }

        val textDataMap = questions?.associateBy({ it.text }, { it.dialogId.toString() }) ?: emptyMap()
        val inlineKeyboard =
            keyboardCreator.createInlineKeyboardMarkup(
                textDataMap,
                CallbackType.STUDENT_IN_QUESTION_MENU_STATE_QUESTION_CHOICE, userId
            )
        val answer = SendMessage(chatId.toString(), "Ваши диалоги:")
        answer.replyMarkup = inlineKeyboard
        return answer
    }

    override fun handleMessage(message: Message): BotApiMethod<*> {
        val command = Command.parseCommand(message.text)
        if (Command.ASK != command) {
            return super.handleMessage(message)
        }
        return handleChannels(message)
    }

    private fun handleChannels(message: Message): BotApiMethod<*> {
        val chatId = message.chatId
        val userId = chatService.getUserIdByChatId(chatId)

        val channels = try {
            eduAssistantClient.getStudentChannels(userId).channels
        } catch (_: Exception) {
            null
        }

        val textDataMap = channels?.associateBy({ it.name }, { it.id.toString() }) ?: emptyMap()
        val inlineKeyboard = keyboardCreator.createInlineKeyboardMarkup(
            textDataMap,
            CallbackType.STUDENT_IN_QUESTION_MENU_STATE_CHANNEL_CHOICE, userId
        )

        val answer = SendMessage(chatId.toString(), "Ваши очереди:")
        answer.replyMarkup = inlineKeyboard
        return answer
    }
}