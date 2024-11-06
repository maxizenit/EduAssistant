package ru.itmo.eduassistant.bot.service

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import ru.itmo.eduassistant.bot.cache.StateCache
import ru.itmo.eduassistant.bot.enm.CallbackType
import ru.itmo.eduassistant.bot.service.state.State
import ru.itmo.eduassistant.bot.service.state.common.LongTimeInactiveState
import ru.itmo.eduassistant.bot.service.state.common.UnauthorizedState
import ru.itmo.eduassistant.bot.util.CallbackUtils
import kotlin.reflect.KClass

@Service
class StateSwitcher(
    private val context: ApplicationContext,
    private val stateCache: StateCache,
    private val callbackUtils: CallbackUtils
) {

    fun switchState(chatId: Long, stateClass: KClass<out State>): BotApiMethod<*> {
        return switchState(chatId, stateClass, null)
    }

    fun switchState(chatId: Long, stateClass: KClass<out State>, callbackType: CallbackType?): BotApiMethod<*> {
        val state = context.getBean(stateClass.java)
        stateCache.put(chatId, state)
        return state.initState(chatId)
    }

    fun switchStateFromCallback(callbackQuery: CallbackQuery, stateClass: KClass<out State>): BotApiMethod<*> {
        val callbackData = callbackUtils.parseCallbackData(callbackQuery.data)
        val state = context.getBean(stateClass.java)
        val chatId = callbackData.chatId
        stateCache.put(chatId, state)
        return state.handleCallback(callbackQuery)
    }

    fun switchToStartState(chatId: Long): BotApiMethod<*> {
        return switchState(chatId, UnauthorizedState::class)
    }

    fun switchToLongTimeInactiveState(chatId: Long): BotApiMethod<*> {
        return switchState(chatId, LongTimeInactiveState::class)
    }
}