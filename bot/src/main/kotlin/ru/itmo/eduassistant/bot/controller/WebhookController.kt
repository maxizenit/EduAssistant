package ru.itmo.eduassistant.bot.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import ru.itmo.eduassistant.bot.service.CallbackQueryHandler
import ru.itmo.eduassistant.bot.service.MessageHandler

@RestController
class WebhookController(
    private val messageHandler: MessageHandler,
    private val callbackQueryHandler: CallbackQueryHandler
) {

    @PostMapping("/callback/update")
    fun onWebhookUpdateReceived(@RequestBody update: Update): BotApiMethod<*> {
        if (update.hasCallbackQuery()) {
            return callbackQueryHandler.handleCallbackQuery(update.callbackQuery)
        }
        return messageHandler.handleMessage(update.message)
    }
}