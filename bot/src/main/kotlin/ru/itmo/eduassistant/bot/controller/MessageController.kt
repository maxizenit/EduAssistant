package ru.itmo.eduassistant.bot.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itmo.eduassistant.commons.client.telegram.dto.SendMessageRequest

@RestController
@RequestMapping("/messages")
class MessageController {
    @PostMapping
    fun receiveMessage(@RequestBody request: SendMessageRequest): ResponseEntity<*> {
        return ResponseEntity.ok(null)
    }
}
