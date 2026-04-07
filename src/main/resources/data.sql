-- Opret et par test-brugere
INSERT INTO user (name, email, password, age) VALUES ('Peter Parker', 'peter@spider.com', '$2a$10$BqbYDg8YnqUfS5RGsvZOMujgnPoSfe2yn0rQrdr8bIXFtOLprSvoS', 17);
INSERT INTO user (name, email, password, age) VALUES ('Bruce Wayne', 'bruce@batman.com', '$2a$10$BqbYDg8YnqUfS5RGsvZOMujgnPoSfe2yn0rQrdr8bIXFtOLprSvoS', 35);
-- koder er 123456

-- Opret et par test-film i biografen
INSERT INTO movie (title, genre, age_limit, price) VALUES ('Gurli Gris i Biografen', 'Børn', 0, 75.0);
INSERT INTO movie (title, genre, age_limit, price) VALUES ('Busters Verden', 'Familie', 7, 85.0);
INSERT INTO movie (title, genre, age_limit, price) VALUES ('Harry Potter 1', 'Eventyr', 13, 95.0);
INSERT INTO movie (title, genre, age_limit, price) VALUES ('The Joker', 'Drama/Thriller', 15, 110.0);
INSERT INTO movie (title, genre, age_limit, price) VALUES ('Motorsavsmassakren', 'Gyser', 18, 110.0);

-- Vi kan oprette en test-reservation (Peter køber 2 billetter til Busters Verden):
INSERT INTO reservation (user_id, reservation_date, total_price) VALUES (1, NOW(), 250.0);

INSERT INTO ticket (reservation_id, movie_id, show_date, show_time) VALUES (1, 2, '2024-12-24', '17:00:00');
INSERT INTO ticket (reservation_id, movie_id, show_date, show_time) VALUES (1, 2, '2024-12-24', '17:00:00');

-- Opret snacks med type og størrelse
INSERT INTO snack (type, size, price) VALUES ('Popcorn', 'Lille', 35.0);
INSERT INTO snack (type, size, price) VALUES ('Popcorn', 'Mellem', 45.0);
INSERT INTO snack (type, size, price) VALUES ('Popcorn', 'Stor', 55.0);
INSERT INTO snack (type, size, price) VALUES ('Sodavand', 'Lille', 30.0);
INSERT INTO snack (type, size, price) VALUES ('Sodavand', 'Mellem', 35.0);
INSERT INTO snack (type, size, price) VALUES ('Sodavand', 'Stor', 45.0);
INSERT INTO snack (type, size, price) VALUES ('Slik', 'Standard', 35.0);

-- Vi giver Peter (Reservation 1) en Stor Sodavand og en Stor Popcorn (Menu!):
INSERT INTO reservation_snack (reservation_id, snack_id) VALUES (1, 6); -- Stor Sodavand
INSERT INTO reservation_snack (reservation_id, snack_id) VALUES (1, 3); -- Stor Popcorn