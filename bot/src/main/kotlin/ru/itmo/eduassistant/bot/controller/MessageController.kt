package ru.itmo.eduassistant.bot.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.itmo.eduassistant.bot.service.ChatService
import ru.itmo.eduassistant.bot.util.MessageUtils
import ru.itmo.eduassistant.commons.dto.telegram.SendNotificationRequest

@RestController
@RequestMapping("/messages")
class MessageController(
    private val chatService: ChatService,
    private val messageUtils: MessageUtils
) {

    @PostMapping
    fun receiveMessage(@RequestBody request: SendNotificationRequest): ResponseEntity<*> {
        request.recipientIds.forEach {
            try {
                val chatId = chatService.getChatIdByUserId(it)
                messageUtils.sendMessage(SendMessage(chatId.toString(), request.notification.text))
            } catch (_: Exception) {
            }
        }

        return ResponseEntity.ok(null)
    }
}
