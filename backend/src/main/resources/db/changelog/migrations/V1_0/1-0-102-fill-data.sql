-- Преподаватели
INSERT
INTO users(username, role, fio)
VALUES ('teacher1', 'role1', 'fio1'),
       ('teacher2', 'role2', 'fio2');

INSERT INTO groups(name)
VALUES ('Группа1'),
       ('Группа2');

INSERT INTO subjects(name, teacher_id)
VALUES ('', 1),
       ('', 2);

INSERT INTO groups_to_subjects(group_id, subject_id)
VALUES (1, 1),
       (2, 2);

-- Студенты
INSERT
INTO users(username, role, fio, group_id)
VALUES ('username1', 'role1', 'fio1', 3),
       ('username2', 'role2', 'fio2', 4);

-- дополнить данными