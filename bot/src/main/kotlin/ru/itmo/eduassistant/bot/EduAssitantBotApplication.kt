package ru.itmo.eduassistant.bot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class EduAssistantBotApplication

fun main(args: Array<String>) {
    runApplication<EduAssistantBotApplication>(*args)
}