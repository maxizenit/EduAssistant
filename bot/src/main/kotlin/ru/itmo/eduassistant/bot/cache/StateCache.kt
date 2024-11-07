package ru.itmo.eduassistant.bot.cache

import org.springframework.cache.CacheManager
import org.springframework.stereotype.Component
import ru.itmo.eduassistant.bot.config.CacheConfig
import ru.itmo.eduassistant.bot.service.state.State

@Component
class StateCache(cacheManager: CacheManager) :
    AbstractCache<Long, State>(cacheManager.getCache(CacheConfig.STATE_CACHE) ?: throw NullPointerException())