package ru.itmo.eduassistant.bot.service.state.student

import ru.itmo.eduassistant.bot.annotation.BotState
import ru.itmo.eduassistant.bot.enm.Command
import ru.itmo.eduassistant.bot.service.StateSwitcher
import ru.itmo.eduassistant.bot.service.state.State
import ru.itmo.eduassistant.bot.util.KeyboardCreator
import ru.itmo.eduassistant.bot.util.MessageUtils

@BotState
class StudentInNotificationMenuState(
    stateSwitcher: StateSwitcher,
    messageUtils: MessageUtils,
    keyboardCreator: KeyboardCreator
) : State(
    stateSwitcher, keyboardCreator, messageUtils, mapOf(
        Command.BACK to StudentInMainMenuState::class
    )
)