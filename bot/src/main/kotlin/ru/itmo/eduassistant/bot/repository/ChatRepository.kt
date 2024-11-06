package ru.itmo.eduassistant.bot.repository

import org.springframework.data.repository.CrudRepository
import ru.itmo.eduassistant.bot.model.entity.Chat

interface ChatRepository : CrudRepository<Chat, Long> {

    fun existsByUserId(userId: Long): Boolean

    fun findByUserId(userId: Long): Chat

    fun deleteChatsByUserId(userId: Long)
}