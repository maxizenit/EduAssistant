package ru.itmo.eduassistant.bot.service

import org.springframework.stereotype.Service
import ru.itmo.eduassistant.bot.model.entity.Chat
import ru.itmo.eduassistant.bot.repository.ChatRepository

@Service
class ChatService(private val chatRepository: ChatRepository) {

    fun saveChatUserBind(chatId: Long, userId: Long) {
        if (!chatRepository.existsById(chatId) || !chatRepository.existsByUserId(userId)) {
            chatRepository.deleteChatsByUserId(userId)
            val chat = Chat(chatId, userId)
            chatRepository.save(chat)
        }
    }

    fun getUserIdByChatId(chatId: Long): Long {
        return chatRepository.findById(chatId).get().userId
    }

    fun getChatIdByUserId(userId: Long): Long {
        return chatRepository.findByUserId(userId)?.id ?: throw NullPointerException()
    }
}