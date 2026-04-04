-- 1. Slet krydstabellerne først, da de afhænger af de andre tabeller
DROP TABLE IF EXISTS reservation_snack;
DROP TABLE IF EXISTS reservation_movie;

-- 2. Slet derefter reservationer, da de afhænger af brugere
DROP TABLE IF EXISTS reservation;

-- 3. Til sidst kan vi trygt slette fundamentet
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS snack;
DROP TABLE IF EXISTS movie;

CREATE TABLE user (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(50) NOT NULL,
                      email VARCHAR(50) NOT NULL UNIQUE,
                      age INT NOT NULL
);

-- 2. Film tabellen
CREATE TABLE movie (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       title VARCHAR(100) NOT NULL,
                       genre VARCHAR(50) NOT NULL,
                       age_limit INT NOT NULL,
                       price DOUBLE NOT NULL
);

-- 3. Reservations tabellen (Bemærk: Intet movie_id her!)
CREATE TABLE reservation (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             user_id INT NOT NULL,
                             reservation_date DATETIME NOT NULL,
                             total_price DOUBLE NOT NULL,
                             FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 4. KRYDSTABELLEN (Mange-til-Mange relation)
CREATE TABLE reservation_movie (
                                   reservation_id INT NOT NULL,
                                   movie_id INT NOT NULL,
                                   FOREIGN KEY (reservation_id) REFERENCES reservation(id),
                                   FOREIGN KEY (movie_id) REFERENCES movie(id)
);

-- Kiosk-menuen
CREATE TABLE snack (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       type VARCHAR(50) NOT NULL,
                       size VARCHAR(50) NOT NULL,
                       price DOUBLE NOT NULL
);

-- Krydstabel til Snacks
CREATE TABLE reservation_snack (
                                   reservation_id INT NOT NULL,
                                   snack_id INT NOT NULL,
                                   FOREIGN KEY (reservation_id) REFERENCES reservation(id),
                                   FOREIGN KEY (snack_id) REFERENCES snack(id)
);