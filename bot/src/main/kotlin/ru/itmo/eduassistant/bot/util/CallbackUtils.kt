package ru.itmo.eduassistant.bot.util

import org.springframework.stereotype.Component
import ru.itmo.eduassistant.bot.enm.CallbackType
import ru.itmo.eduassistant.bot.model.CallbackData

@Component
class CallbackUtils {

    fun createStringCallbackData(callbackType: CallbackType, userId: Long, data: String): String {
        return "${callbackType.ordinal} $userId $data"
    }

    fun parseCallbackData(stringCallbackData: String): CallbackData {
        val callbackType = CallbackType.entries[stringCallbackData.split(" ")[0].toInt()]
        val chatId = stringCallbackData.split(" ")[1].toLong()
        val data = stringCallbackData.split(" ")[2]
        return CallbackData(callbackType, chatId, data)
    }
}