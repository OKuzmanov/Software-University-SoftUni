-- https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-initialization.using-basic-sql-scripts

-- userEntity roles
INSERT INTO roles (id, name)
VALUES (1, 'ADMIN'),
(2, 'MODERATOR'),
(3, 'USER');

-- populating userEntities (problem with encryption of password)
-- INSERT INTO userEntities (id, first_name, last_name, email, password, username)
-- VALUES (1, 'Oleg', 'Kuzmanov', 'ok@gmail.com', '12345', 'oleg4o'),
-- (2, 'Albena', 'Yazovska', 'ay@gmail.com', '12345', 'benati'),
-- (3, 'Pesho', 'Peshev', 'pp@gmail.com', '12345', 'pesho'),
-- (4, 'Ivan', 'Ivanov', 'ii@gmail.com', '12345', 'vanko');

-- user_roles populating
-- INSERT INTO users_roles (`user_id`, `role_id`)
-- VALUES (1, 1),
-- (1, 2),
-- (1, 3),
-- (2, 2),
-- (2, 3),
-- (3, 3),
-- (4, 3);

-- populating suppliers
INSERT INTO `purewater_crm_db`.`suppliers` (`address`, `company_name`, `description`, `email`, `phone_number`)
VALUES ('London str.80', 'Brothers Co.', 'The ultimate company for supply of raw materials.', 'brothers.co@gmail.com', '0878456811'),
('Mastricht str.22', 'Fast Supply Ltd.', 'Company for super fast supply of raw materials.', 'fs.ltd@gmail.com', '0972845720'),
('Veliko Turnovo str.12', 'Euro Supply ltd.', 'Company for supply of raw materials in Europe.', 'eusro.supp@gmail.com', '0874562192'),
('Burgas str.51', 'Sea supply ltd.', 'Company for supply of raw materials from overseas.', 'sea.supp@gmail.com', '0878296617');

--populating customers
INSERT INTO `purewater_crm_db`.`customers` (`address`, `company_name`, `description`, `email`, `phone_number`)
VALUES ('Sofia str.Vasil Levski', 'Ganchev and Brothers ltd.', 'A leading distributor of water and other non-alcoholic beverages in Sofia region.', 'gandb@gmail.com', '0878632074'),
('Veliko Turnovo str.15', 'Tsare and Co.', 'A leading distributor of water and other non-alcoholic beverages in Veliko Turnovo region.', 'tsare@gmail.com', '0878456627'),
('Varna str.Morska Gradina', 'Seagull Distributors ltd.', 'A leading distributor of water and other non-alcoholic beverages in Varna region.', 'seagull.distribute@gmail.com', '0878421275');

