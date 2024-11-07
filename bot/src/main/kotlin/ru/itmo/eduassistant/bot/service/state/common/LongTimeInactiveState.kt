package ru.itmo.eduassistant.bot.service.state.common

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import ru.itmo.eduassistant.bot.annotation.BotState
import ru.itmo.eduassistant.bot.service.StateSwitcher
import ru.itmo.eduassistant.bot.service.state.State
import ru.itmo.eduassistant.bot.util.CallbackUtils
import ru.itmo.eduassistant.bot.util.KeyboardCreator
import ru.itmo.eduassistant.bot.util.MessageUtils

@BotState
class LongTimeInactiveState(
    stateSwitcher: StateSwitcher,
    keyboardCreator: KeyboardCreator,
    messageUtils: MessageUtils,
    private val callbackUtils: CallbackUtils
) : State(
    stateSwitcher, keyboardCreator, messageUtils, emptyMap()
) {
    override fun initState(chatId: Long): BotApiMethod<*> {
        val sendMessage = SendMessage(
            chatId.toString(),
            "Вас долго не было. Начните сначала"
        )
        messageUtils.sendMessage(sendMessage)
        return stateSwitcher.switchState(chatId, UnauthorizedState::class)
    }

    override fun handleMessage(message: Message): BotApiMethod<*> {
        return initState(message.chatId)
    }

    override fun handleCallback(callback: CallbackQuery): BotApiMethod<*> {
        val chatId = callbackUtils.parseCallbackData(callback.data).chatId
        val sendMessage = SendMessage(
            chatId.toString(),
            "Вас долго не было. Начните сначала"
        )
        messageUtils.sendMessage(sendMessage)
        return stateSwitcher.switchState(chatId, UnauthorizedState::class)
    }
}