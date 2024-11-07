-- Преподаватели
INSERT
INTO users(username, role, fio, telegram_id)
VALUES ('teacher1', 'TEACHER', 'fio1', 435345),
       ('teacher2', 'TEACHER', 'fio2', 435435);

-- Каналы
INSERT INTO channels(name, teacher_id, description)
VALUES ('Углубленная Java', 1, 'Курс предназначен для студентов, имеющих базовые знания в области программирования на языке Java. '),
       ('Программная Инженерия', 2, 'В курсе рассматриваются все составляющие успешного проекта от
анализа требований и проектирования пользовательского интерфейса и до
построения наиболее подходящего под проект и команду процесса
разработки.');



-- Студенты
INSERT
INTO users(username, role, fio, telegram_id)
VALUES ('username1', 'STUDENT', 'fio1', 13242),
       ('username2', 'STUDENT', 'fio2', 543434),
       ('username3', 'STUDENT', 'fio3', 113242),
       ('username4', 'STUDENT', 'fio4', 2543434);

INSERT INTO users_to_channel(user_id, channel_id)
VALUES (3, 1),
       (3, 2),
       (4, 1),
       (5, 2),
       (6, 2);

-- Диалоги
INSERT
INTO dialogs(is_closed, author_id, first_message, recipient_id, channel_id)
VALUES (false, 3, 'Прив, че дел?', null, 1),
       (true, 4, 'Отвянь!', null, 2);

-- Сообщения
INSERT
INTO messages(body, datetime, author_id, recipient_id, dialog_id)
VALUES ('Прив, че дел?', dateadd(hour, -3, NOW()), 3, 1, 1),
       ('Отвянь!', dateadd(hour, -2, NOW()), 1, 3, 1),
       ('А что если нет?', dateadd(hour, -1, NOW()), 3, 1, 1),
       ('Дай списать домашку?', dateadd(hour, -5, NOW()), 4, 2, 2),
       ('Пакетик брать будете?', dateadd(hour, -3, NOW()), 2, 4, 2),
       ('Спасибо! ♥', dateadd(hour, -1, NOW()), 4, 2, 2);

-- Уведомления
INSERT
INTO notifications(body, datetime, channel_id, is_archived)
VALUES ('Тестирую отправку уведомлений', dateadd(hour, -5, NOW()), 1, false),
       ('Ой, а где оно?', dateadd(day, -1, NOW()), 1, true),
       ('Testing testing всем автомат,\nзавтра можете не приходить', dateadd(hour, -3, NOW()), 1, false),
       ('Хочу пиццу!', dateadd(hour, -1, NOW()), 2, false);
