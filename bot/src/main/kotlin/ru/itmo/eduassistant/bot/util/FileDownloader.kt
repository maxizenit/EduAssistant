package ru.itmo.eduassistant.bot.util

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import org.telegram.telegrambots.meta.api.objects.ApiResponse
import org.telegram.telegrambots.meta.api.objects.File
import ru.itmo.eduassistant.bot.service.EduAssistantBot

@Component
class FileDownloader(
    private val restTemplate: RestTemplate,
    private val eduAssistantBot: EduAssistantBot
) {
    @Value("\${bot.api-url}")
    private lateinit var apiUrl: String

    @Value("\${bot.token}")
    private lateinit var token: String

    fun getFile(
        fileId: String,
    ): java.io.File {
        return eduAssistantBot.downloadFile(getFilePath(fileId))
    }

    private fun getFilePath(fileId: String): String {
        val url = "$apiUrl/bot$token/getFile"
        val uri = UriComponentsBuilder.fromHttpUrl(url).queryParam("file_id", fileId).encode().build().toUri()

        val responseBody = restTemplate.exchange(uri,
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<ApiResponse<File>>() {}).body
        val file = responseBody?.result ?: throw NullPointerException()

        return file.filePath
    }
}