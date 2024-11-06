package ru.itmo.eduassistant.bot.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itmo.eduassistant.commons.dto.telegram.SendNotificationRequest

@RestController
@RequestMapping("/messages")
class MessageController {

    @PostMapping
    fun receiveMessage(@RequestBody request: SendNotificationRequest): ResponseEntity<*> {
        return ResponseEntity.ok(null)
    }
}
