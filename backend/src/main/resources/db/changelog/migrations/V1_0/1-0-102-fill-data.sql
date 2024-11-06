-- Преподаватели
INSERT
INTO users(username, role, fio)
VALUES ('teacher1', 'TEACHER', 'fio1'),
       ('teacher2', 'TEACHER', 'fio2');

INSERT INTO groups(name)
VALUES ('Группа1'),
       ('Группа2');

INSERT INTO subjects(name, teacher_id)
VALUES ('ИЗО', 1),
       ('Физкультура', 2);

INSERT INTO groups_to_subjects(group_id, subject_id)
VALUES (1, 1),
       (2, 2);

-- Студенты
INSERT
INTO users(username, role, fio, group_id)
VALUES ('username1', 'STUDENT', 'fio1', 3),
       ('username2', 'STUDENT', 'fio2', 4);

-- Диалоги
INSERT
INTO dialogs(is_closed, author_id, first_message, recipient_id, subject_id)
VALUES (false, 3, 1, null, 1),
       (true, 4, 2, null, 2);

-- Сообщения
INSERT
INTO messages(body, datetime, author_id, recipient_id, dialog_id)
VALUES ('Прив, че дел?', dateadd(hour, -3, NOW()), 3, 1, 1),
       ('Отвянь!', dateadd(hour, -2, NOW()), 1, 3, 1),
       ('А что если нет?', dateadd(hour, -1, NOW()), 3, 1, 1),
       ('Дай списать домашку?', dateadd(hour, -5, NOW()), 4, 2, 2),
       ('Пакетик брать будете?', dateadd(hour, -3, NOW()), 2, 4, 2),
       ('Спасибо! ♥', dateadd(hour, -1, NOW()), 4, 2, 2);

INSERT
INTO templates(body)
VALUES ('Вот домашка за %date:'),
       ('Внимание! Внимание!\nСпасибо за внимание!');

INSERT
INTO notifications(template_id, body, datetime, subject_id, is_archived)
VALUES (1, 'Тестирую отправку уведомлений', dateadd(hour, -5, NOW()), 1, false),
       (null, 'Ой, а где оно?', dateadd(day, -1, NOW()), 1, true),
       (null, 'Testing testing всем автомат,\nзавтра можете не приходить', dateadd(hour, -3, NOW()), 1, false),
       (2, 'Хочу пиццу!', dateadd(hour, -1, NOW()), 2, false);