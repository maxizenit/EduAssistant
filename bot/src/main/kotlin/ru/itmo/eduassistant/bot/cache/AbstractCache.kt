package ru.itmo.eduassistant.bot.cache

import org.springframework.cache.Cache

abstract class AbstractCache<K : Any, V : Any>(private val cache: Cache) {

    open fun get(key: K): V? {
        @Suppress("UNCHECKED_CAST")
        return cache.get(key)?.get() as? V
    }

    fun put(key: K, value: V) {
        cache.put(key, value)
    }

    fun evict(key: K) {
        cache.evict(key)
    }
}