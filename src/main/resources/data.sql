-- Opret et par test-brugere
INSERT INTO user (name, email, age) VALUES ('Peter Parker', 'peter@spider.com', 17);
INSERT INTO user (name, email, age) VALUES ('Bruce Wayne', 'bruce@batman.com', 35);

-- Opret et par test-film i biografen
INSERT INTO movie (title, genre, age_limit, price) VALUES ('Gurli Gris i Biografen', 'Børn', 0, 75.0);
INSERT INTO movie (title, genre, age_limit, price) VALUES ('Busters Verden', 'Familie', 7, 85.0);
INSERT INTO movie (title, genre, age_limit, price) VALUES ('Harry Potter 1', 'Eventyr', 13, 95.0);
INSERT INTO movie (title, genre, age_limit, price) VALUES ('The Joker', 'Drama/Thriller', 15, 110.0);
INSERT INTO movie (title, genre, age_limit, price) VALUES ('Motorsavsmassakren', 'Gyser', 18, 110.0);

-- Vi kan oprette en test-reservation (Peter køber 2 billetter til Busters Verden):
INSERT INTO reservation (user_id, reservation_date, total_price) VALUES (1, NOW(), 250.0);

-- Og her binder vi filmene til reservationen via krydstabellen!
INSERT INTO reservation_movie (reservation_id, movie_id) VALUES (1, 2); -- Billet 1
INSERT INTO reservation_movie (reservation_id, movie_id) VALUES (1, 2); -- Billet 2

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