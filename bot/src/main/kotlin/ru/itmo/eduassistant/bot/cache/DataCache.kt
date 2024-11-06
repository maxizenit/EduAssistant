package ru.itmo.eduassistant.bot.cache

import org.springframework.cache.CacheManager
import org.springframework.stereotype.Component
import ru.itmo.eduassistant.bot.config.CacheConfig
import ru.itmo.eduassistant.bot.enm.DataType
import java.util.*

@Component
class DataCache(cacheManager: CacheManager) :
    AbstractCache<Long, EnumMap<DataType, Any>>(
        cacheManager.getCache(CacheConfig.DATA_CACHE) ?: throw NullPointerException()
    ) {

    fun getInputDataValue(chatId: Long, dataType: DataType): Any? {
        val data = get(chatId) ?: return null
        return data[dataType]
    }

    fun putInputDataValue(chatId: Long, dataType: DataType, value: Any) {
        var data = get(chatId)
        if (data == null) {
            data = EnumMap<DataType, Any>(DataType::class.java)
            put(chatId, data)
        }
        data[dataType] = value
    }

    fun removeInputDataValue(chatId: Long, dataType: DataType) {
        var data = get(chatId)
        if (data == null) {
            data = EnumMap<DataType, Any>(DataType::class.java)
        }
        data.remove(dataType)
    }
}