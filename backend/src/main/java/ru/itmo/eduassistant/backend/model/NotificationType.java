package ru.itmo.eduassistant.backend.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NotificationType {
    QUEUE_OPENED("📝 Доступна новая очередь!\n\n Открыта запись в очередь [%s]. Если вы хотите успеть записаться, рекомендуем сделать это как можно раньше!\n🕒Запись доступна до: [%s]"),
    ANNOUNCEMENT("🚨 Важная информация [%s]\nПоявилось новое уведомление по предмету:\n\n %s\n\nПожалуйста, ознакомьтесь.");

    private final String template;

    public String apply(String subjectName, String text) {
        return template.formatted(subjectName, text);
    }
}
