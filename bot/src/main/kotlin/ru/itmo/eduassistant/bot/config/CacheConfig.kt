package ru.itmo.eduassistant.bot.config

import com.google.common.cache.CacheBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cacheManager(@Value("\${cache.expiration-time}") expirationTime: java.time.Duration): CacheManager {
        return object : ConcurrentMapCacheManager(STATE_CACHE, DATA_CACHE) {
            override fun createConcurrentMapCache(name: String): Cache {
                return ConcurrentMapCache(
                    name,
                    CacheBuilder.newBuilder()
                        .expireAfterWrite(expirationTime)
                        .expireAfterAccess(expirationTime)
                        .build<Any, Any>()
                        .asMap(),
                    false
                )
            }
        }
    }

    companion object {
        const val STATE_CACHE = "stateCache"
        const val DATA_CACHE = "dataCache"
    }
}