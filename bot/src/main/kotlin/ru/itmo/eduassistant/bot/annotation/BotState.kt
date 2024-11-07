package ru.itmo.eduassistant.bot.annotation

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class BotState(
    @get:AliasFor(annotation = Component::class) val value: String = ""
)