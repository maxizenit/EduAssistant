package ru.itmo.eduassistant.bot.service.state.student

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
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
class StudentInChannelMenuState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    private val chatService: ChatService,
    private val eduAssistantClient: EduAssistantClient
) : State(
    stateSwitcher, keyboardCreator, messageUtils, mapOf(
        Command.SUBSCRIBE_ON_CHANNEL to StudentInSubscribeOnChannelState::class,
        Command.BACK to StudentInMainMenuState::class
    )
) {
    override fun initState(chatId: Long): BotApiMethod<*> {
        val sendMessage = SendMessage(chatId.toString(), "Выберите канал")
        sendMessage.replyMarkup = keyboard
        messageUtils.sendMessage(sendMessage)
        return createChannelsListMessage(chatId)
    }

    private fun createChannelsListMessage(chatId: Long): SendMessage {
        val userId = chatService.getUserIdByChatId(chatId)

        val channels = try {
            eduAssistantClient.getStudentChannels(userId).channels
        } catch (_: Exception) {
            null
        }

        val textDataMap = channels?.associateBy({ it.name }, { it.id.toString() }) ?: emptyMap()
        val inlineKeyboard = keyboardCreator.createInlineKeyboardMarkup(
            textDataMap,
            CallbackType.STUDENT_IN_CHANNEL_MENU_STATE_CHANNEL_CHOICE, userId
        )

        val sendMessage = SendMessage(chatId.toString(), "Ваши каналы:")
        sendMessage.replyMarkup = inlineKeyboard
        return sendMessage
    }
}