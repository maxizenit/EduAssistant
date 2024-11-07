package ru.itmo.eduassistant.backend.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NotificationType {
    QUEUE_OPENED("📝 Доступна новая очередь!\n\n Открыта запись в очередь [%s]. Если вы хотите успеть записаться, рекомендуем сделать это как можно раньше!\n"),
    ANNOUNCEMENT("🚨 Важная информация [%s]\nПоявилось новое уведомление по предмету:\n\n %s\n\nПожалуйста, ознакомьтесь."),
    YOUR_TURN("⏰ Уважаемый студент, ваша очередь подошла!\n\uD83D\uDCCC Предмет: [%s]\n\n Преподаватель вас ожидает, желаем удачи!"),
    YOUR_TURN_NEXT("\uD83D\uDCE2 Уважаемый студент, вы следующий в очереди!\nПредмет: [%s]\n\n\uD83D\uDD39Скоро преподаватель вас пригласит, пожалуйста, будьте готовы!");

    private final String template;

    public String apply(String queueName) {
        return template.formatted(queueName);
    }

    public String apply(String queueName, String text) {
        return template.formatted(queueName, text);
    }
}
