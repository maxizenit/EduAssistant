# EduAssistant

**BotAssistant** — это приложение для автоматизации коммуникации, уведомлений и создания очередей для студентов в институте. Система состоит из двух компонентов:

1. **Telegram Bot** — интерфейс для студентов для взаимодействия с системой, получения уведомлений и регистрации в очередях.
2. **Backend** — серверная часть, которая управляет логикой очередей, уведомлениями и взаимодействует с базой данных.

## Основные особенности

- **Коммуникация с ботом**: Студенты могут взаимодействовать с ботом, чтобы быть информированными о статусе очередей и других событиях.
- **Очереди**: Создание, управление и отслеживание очередей для студентов. Студенты могут регистрироваться в очереди и получать уведомления.
- **Уведомления**: Отправка уведомлений студентам через бота о событиях, связанных с очередями.

## Описание
Проект был разработан в рамках хакатона ИТМО ИПКН DevDays Fall 5 - 8 ноября 2024.
Участники проекта:

- @vevasilev - TeamLead 
- @alefunt - bot development 
- @maxizenit - bot development 
- @timofeyreedtz - backend development 
- @romankuryshev - backend development

## Структура приложения

1. **Telegram Bot**
    - Разработан с использованием Kotlin и библиотеки для создания Telegram-ботов.
    - Обрабатывает команды студентов, взаимодействует с сервером через API и отправляет уведомления.

2. **Backend**
    - Серверная часть на Java (Spring Boot).
    - Управляет базой данных, а также взаимодействует с ботом через API.
    - Реализует логику создания и отслеживания очередей студентов.

## Установка

### Запуск серверной части (Java)

1. Клонируйте репозиторий бэкенда:
   ```bash
   git clone git@github.com:maxizenit/EduAssistant.git
   ```
2. Запуск серверной части
   ```bash
   ./gradlew backend:bootRun
   ```

### Запуск Telegram Bot

Для запуска бота необходимо определить следующие переменные (указаны
в [application.yaml](bot/src/main/resources/application.yaml)):

* bot.webhook-path - адрес вебхука
* bot.username - юзернейм бота, выданный [@BotFather](https://t.me/BotFather)
* bot.token - токен бота, выданный [@BotFather](https://t.me/BotFather)

  И выполнить:
  ```bash
  ./gradlew bot:bootRun
  ```

## Получение адреса вебхука для локального запуска

Для локального запуска бота можно воспользоваться [ngrok.io](https://ngrok.io). Необходимо:

1. Запустить приложение Ngrok
2. Запустить Ngrok командой `ngrok http <port>`, в качестве значения `port` необходимо использовать порт приложения бота
3. Скопировать сгенерированную ссылку из Forwarding и вставить её как значение в `bot.webhook-path`
4. Запустить приложение бота
