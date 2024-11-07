package ru.itmo.eduassistant.backend.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NotificationType {
    QUEUE_OPENED("📝 Доступна новая очередь!\n\n Открыта запись в очередь [%s]. Запись продлится до [%s], рекомендуем подтвердить участие как можно раньше!"),
    ANNOUNCEMENT("🚨 Важная информация!\nПоявилось новое уведомление по предмету [%s].\nПожалуйста, ознакомьтесь."),
    YOUR_TURN("⏰ Уважаемый студент, ваша очередь подошла!\n\uD83D\uDCCC Предмет: [%s]\n\n Преподаватель вас ожидает, желаем удачи!"),
    YOUR_TURN_NEXT("\uD83D\uDCE2 Уважаемый студент, вы следующий в очереди!\nПредмет: [%s]\n\n\uD83D\uDD39Скоро преподаватель вас пригласит, пожалуйста, будьте готовы!"),
    NEW_MESSAGE("💬 В канале [%s] появилось новое сообщение!\nОт [%s]"),
    NEW_QUESTION("💬 Новый вопрос по предмету [%s].\nОт [%s]");

    private final String template;

    public String apply(String queueName) {
        return template.formatted(queueName);
    }

    public String apply(String queueName, String text) {
        return template.formatted(queueName, text);
    }
}
