package ru.itmo.eduassistant.bot.service.state

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import ru.itmo.eduassistant.bot.enm.Command
import ru.itmo.eduassistant.bot.service.StateSwitcher
import ru.itmo.eduassistant.bot.util.KeyboardCreator
import ru.itmo.eduassistant.bot.util.MessageUtils
import kotlin.reflect.KClass

abstract class State(
    protected val stateSwitcher: StateSwitcher,
    protected val keyboardCreator: KeyboardCreator,
    protected val messageUtils: MessageUtils,
    protected val nextCommandStateMap: Map<Command, KClass<out State>>
) {
    protected val keyboard by lazy {
        createKeyboard()
    }

    open fun initState(chatId: Long): BotApiMethod<*> {
        val sendMessage = SendMessage(chatId.toString(), "Выберите действие")
        sendMessage.replyMarkup = keyboard
        return sendMessage
    }

    open fun handleMessage(message: Message): BotApiMethod<*> {
        val chatId = message.chatId
        //messageUtils.deleteMessage(chatId, message.messageId)

        val command = Command.parseCommand(message.text)
        if (command == null || !nextCommandStateMap.keys.contains(command)) {
            val sendMessage = SendMessage(chatId.toString(), "Неизвестная команда")
            sendMessage.replyMarkup = keyboard
            return sendMessage
        }
        return stateSwitcher.switchState(chatId, nextCommandStateMap[command]!!)
    }

    open fun handleCallback(callback: CallbackQuery): BotApiMethod<*> {
        throw RuntimeException()
    }

    open fun createKeyboard(): ReplyKeyboardMarkup {
        val buttons = nextCommandStateMap.keys.map { listOf(it.text) }.toList()
        return keyboardCreator.createReplyKeyboardMarkup(buttons)
    }
}