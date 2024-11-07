-- Преподаватели
INSERT
INTO users(username, role, fio, telegram_id)
VALUES ('ivan_ivanov', 'TEACHER', 'Иван Иванов', 101010),
       ('petr_petrov', 'TEACHER', 'Петр Петров', 202020),
       ('elena_smirnova', 'TEACHER', 'Елена Смирнова', 303030),
       ('alexandra_orlova', 'TEACHER', 'Александра Орлова', 404040),
       ('dmitry_kirilov', 'TEACHER', 'Дмитрий Кирилов', 505050);

-- Каналы
INSERT INTO channels(name, teacher_id, description)
VALUES ('Углубленное программирование на Java', 1, 'Курс для студентов, имеющих уверенные базовые знания по программированию на Java.'),
       ('Программная инженерия и проектирование', 2, 'Курс охватывает анализ требований, проектирование интерфейсов и процессы разработки.'),
       ('Основы микросервисов', 3, 'Изучение принципов разработки и применения микросервисной архитектуры.'),
       ('Функциональное программирование', 4, 'Курс для студентов, желающих углубить знания в функциональных подходах.'),
       ('Алгоритмы и структуры данных', 5, 'Изучение алгоритмов и структур данных с применением Java.');

-- Студенты
INSERT
INTO users(username, role, fio, telegram_id)
VALUES ('alexey_smirnov', 'STUDENT', 'Алексей Смирнов', 303030),
       ('maria_petrova', 'STUDENT', 'Мария Петрова', 404040),
       ('sergey_ivanov', 'STUDENT', 'Сергей Иванов', 505050),
       ('olga_sidorova', 'STUDENT', 'Ольга Сидорова', 606060),
       ('dmitry_kuznetsov', 'STUDENT', 'Дмитрий Кузнецов', 707070),
       ('irina_volkova', 'STUDENT', 'Ирина Волкова', 808080),
       ('andrey_kuzmin', 'STUDENT', 'Андрей Кузьмин', 909090),
       ('natalya_zhukova', 'STUDENT', 'Наталья Жукова', 101112),
       ('mikhail_koltsov', 'STUDENT', 'Михаил Кольцов', 121314),
       ('ekaterina_belova', 'STUDENT', 'Екатерина Белова', 151617);

INSERT INTO users_to_channel(user_id, channel_id)
VALUES (3, 1),
       (3, 2),
       (4, 1),
       (5, 2),
       (6, 2),
       (7, 3),
       (8, 1),
       (9, 4),
       (10, 5),
       (5, 4),
       (6, 3),
       (7, 5),
       (9, 1),
       (10, 2);

-- Диалоги
INSERT
INTO dialogs(is_closed, author_id, first_message, recipient_id, channel_id)
VALUES (false, 3, 'Привет, есть минутка?', null, 1),
       (true, 4, 'Не отвлекай, пожалуйста!', null, 2),
       (false, 5, 'Можно задать вопрос?', null, 3),
       (false, 6, 'Как подготовиться к экзамену?', null, 1),
       (true, 7, 'Есть проблемы с проектом, нужна помощь', null, 3),
       (false, 8, 'Можете объяснить задание подробнее?', null, 4),
       (true, 9, 'Спасибо за лекцию, но есть вопросы', null, 5);

-- Сообщения
INSERT
INTO messages(body, datetime, author_id, recipient_id, dialog_id)
VALUES ('Привет, есть минутка?', dateadd(hour, -3, NOW()), 3, 1, 1),
       ('В каком формате сдавать проект?', dateadd(hour, -2, NOW()), 1, 3, 1),
       ('А если не успеваю?', dateadd(hour, -1, NOW()), 3, 1, 1),
       ('Можешь помочь с задачей?', dateadd(hour, -5, NOW()), 4, 2, 2),
       ('Задание сдаем через GitHub', dateadd(hour, -3, NOW()), 2, 4, 2),
       ('Спасибо большое!', dateadd(hour, -1, NOW()), 4, 2, 2),
       ('Какие материалы подготовить?', dateadd(hour, -4, NOW()), 5, 3, 3),
       ('Какова структура проекта?', dateadd(hour, -2, NOW()), 3, 5, 3),
       ('Совет по подготовке?', dateadd(hour, -6, NOW()), 6, 1, 4),
       ('Как проходят тесты?', dateadd(hour, -3, NOW()), 7, 2, 5),
       ('Могу сдать позже?', dateadd(hour, -1, NOW()), 8, 4, 6),
       ('Сроки дедлайна?', dateadd(hour, -5, NOW()), 9, 3, 7),
       ('Как запустить проект?', dateadd(hour, -7, NOW()), 10, 1, 6);

-- Уведомления
INSERT
INTO notifications(body, datetime, channel_id, is_archived)
VALUES ('Напоминание о сдаче задания', dateadd(hour, -5, NOW()), 1, false),
       ('Сообщение об архивировании', dateadd(day, -1, NOW()), 1, true),
       ('Завтра лекция отменена, не забудьте!', dateadd(hour, -3, NOW()), 1, false),
       ('Собираемся на обсуждение проекта', dateadd(hour, -1, NOW()), 2, false),
       ('Вебинар по микросервисам', dateadd(hour, -2, NOW()), 3, false),
       ('Архивирование старых материалов', dateadd(day, -2, NOW()), 2, true),
       ('Новая дата экзамена', dateadd(day, -3, NOW()), 4, false),
       ('Лекция перенесена', dateadd(hour, -4, NOW()), 5, false),
       ('Запись доступна для просмотра', dateadd(hour, -6, NOW()), 1, true);
