package ru.itmo.eduassistant.bot.enm

enum class Command(val text: String) {

    START("/start"),
    UPDATE("Обновить"),
    BACK("Назад"),
    MAIN_MENU("Главное меню"),
    NOTIFICATIONS("Уведомления"),
    MY_CHANNELS("Мои каналы"),
    MY_QUESTIONS("Мои вопросы"),
    QUEUE("Очередь на защиту"),
    CREATE_CHANNEL("Создать канал"),
    CREATE_QUEUE("Создать очередь"),
    CREATE_NOTIFICATION("Создать уведомление"),
    ASK("Задать вопрос"),
    CHANNEL_INFO("Информация о канале"),
    SUBSCRIBE_ON_CHANNEL("Зайти в канал");

    companion object {
        fun parseCommand(command: String): Command? {
            return entries.find { it.text.equals(command, true) }
        }
    }
}