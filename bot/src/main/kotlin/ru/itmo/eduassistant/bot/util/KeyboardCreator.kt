package ru.itmo.eduassistant.bot.util

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.itmo.eduassistant.bot.enm.CallbackType

@Component
class KeyboardCreator(private val callbackUtils: CallbackUtils) {

    fun createReplyKeyboardMarkup(textRows: List<List<String>>): ReplyKeyboardMarkup {
        val keyboardRows = ArrayList<KeyboardRow>()
        for (row in textRows) {
            val keyboardRow = KeyboardRow()
            for (button in row) {
                keyboardRow.add(button)
            }
            keyboardRows.add(keyboardRow)
        }

        val replyKeyboardMarkup = ReplyKeyboardMarkup()
        replyKeyboardMarkup.keyboard = keyboardRows
        replyKeyboardMarkup.selective = true
        replyKeyboardMarkup.resizeKeyboard = true
        return replyKeyboardMarkup
    }

    fun createInlineKeyboardMarkup(
        textDataMap: Map<String, String>,
        callbackType: CallbackType,
        userId: Long
    ): InlineKeyboardMarkup {
        return InlineKeyboardMarkup(
            textDataMap.entries.map { mutableListOf(createInlineButton(it.key, callbackType, userId, it.value)) }
        )
    }

    private fun createInlineButton(
        text: String,
        callbackType: CallbackType,
        userId: Long,
        data: String
    ): InlineKeyboardButton {
        val button = InlineKeyboardButton(text)
        button.callbackData = callbackUtils.createStringCallbackData(callbackType, userId, data)
        return button
    }
}