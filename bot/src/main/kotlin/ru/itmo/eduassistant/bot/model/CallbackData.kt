package ru.itmo.eduassistant.bot.model

import ru.itmo.eduassistant.bot.enm.CallbackType

data class CallbackData(val callbackType: CallbackType, val chatId: Long, val data: String)
