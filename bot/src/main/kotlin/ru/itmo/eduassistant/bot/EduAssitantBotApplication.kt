package ru.itmo.eduassistant.bot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EduAssistantBotApplication

fun main(args: Array<String>) {
    runApplication<EduAssistantBotApplication>(*args)
}