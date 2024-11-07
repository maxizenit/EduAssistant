package ru.itmo.eduassistant.bot.enm

enum class Command(val text: String) {

    START("/start"),
    UPDATE("\uD83D\uDD04 Обновить"),
    BACK("◀\uFE0F Назад"),
    MAIN_MENU("\uD83C\uDFE0 Главное меню"),
    NOTIFICATIONS("\uD83D\uDD14 Уведомления"),
    MY_CHANNELS("Мои каналы"),
    MY_QUESTIONS("\uD83D\uDCC4 Мои вопросы"),
    QUEUE("Очередь на защиту"),
    CREATE_CHANNEL("➕ Создать канал"),
    CREATE_QUEUE("➕\uD83D\uDCCB Создать очередь"),
    CREATE_NOTIFICATION("\uD83D\uDD14➕ Создать уведомление"),
    ASK("❓✉\uFE0F Задать вопрос"),
    CHANNEL_INFO("ℹ\uFE0F Информация о канале"),
    SUBSCRIBE_ON_CHANNEL("\uD83D\uDCF2 Зайти в канал");

    companion object {
        fun parseCommand(command: String): Command? {
            return entries.find { it.text.equals(command, true) }
        }
    }
}