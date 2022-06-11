-- https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-initialization.using-basic-sql-scripts

-- user roles
INSERT INTO roles (id, name)
VALUES (1, 'ADMIN'),
(2, 'MODERATOR'),
(3, 'USER');

-- some test users
INSERT INTO users (id, first_name, last_name, email, password, username)
VALUES (1, 'Oleg', 'Kuzmanov', 'ok@gmail.com', '12345', 'oleg4o'),
(2, 'Albena', 'Yazovska', 'ay@gmail.com', '12345', 'benati'),
(3, 'Pesho', 'Peshev', 'pp@gmail.com', '12345', 'pesho'),
(4, 'Ivan', 'Ivanov', 'ii@gmail.com', '12345', 'vanko');

-- user_roles populating
INSERT INTO users_roles (`user_id`, `role_id`)
VALUES (1, 1),
(1, 2),
(1, 3),
(2, 2),
(2, 3),
(3, 3),
(4, 3);