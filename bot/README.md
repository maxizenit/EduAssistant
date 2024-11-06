# Запуск бота

Для запуска бота необходимо определить следующие переменные (указаны
в [application.yaml](src/main/resources/application.yaml)):

* bot.webhook-path - адрес вебхука
* bot.username - юзернейм бота, выданный [@BotFather](https://t.me/BotFather)
* bot.token - токен бота, выданный [@BotFather](https://t.me/BotFather)

## Получение адреса вебхука для локального запуска

Для локального запуска бота можно воспользоваться [ngrok.io](https://ngrok.io). Необходимо:

1. Запустить приложение Ngrok
2. Запустить Ngrok командой `ngrok http <port>`, в качестве значения `port` необходимо использовать порт приложения бота
3. Скопировать сгенерированную ссылку из Forwarding и вставить её как значение в `bot.webhook-path`
4. Запустить приложение бота