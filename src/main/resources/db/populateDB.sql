DELETE FROM user_role;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE global_seq_meal RESTART WITH 100;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2023-02-17 08:30:05', 'BreakFast', 500, 100000),
       ('2023-02-17 12:25:05','Lunch', 500, 100000),
       ('2023-02-17 18:30:05','Dinner', 700, 100000),
       ('2023-02-16 08:15:17', 'Breakfast', 350,100000),
       ('2023-02-16 12:00:05','Lunch',1350,100000),
       ('2023-02-16 19:11:11', 'Dinner', 750, 100000);
