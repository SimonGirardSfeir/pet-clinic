CREATE TABLE IF NOT EXISTS vet (
    id            INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name    VARCHAR(30),
    last_name     VARCHAR(30),
    INDEX(last_name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS speciality (
    id            INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(80),
    INDEX(name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS vet_speciality (
    vet_id        INT(4) UNSIGNED NOT NULL,
    speciality_id INT(4) UNSIGNED NOT NULL,
    FOREIGN KEY(vet_id) REFERENCES vet(id),
    FOREIGN KEY(speciality_id) REFERENCES speciality(id),
    UNIQUE(vet_id, speciality_id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS type (
    id            INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(80),
    INDEX(name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS owner (
     id            INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
     first_name    VARCHAR(30),
     last_name     VARCHAR(30),
     address       VARCHAR(255),
     city          VARCHAR(80),
     telephone     VARCHAR(20),
     INDEX(last_name)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS pet (
    id            INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(80),
    birth_day     DATE,
    type_id       INT(4) UNSIGNED NOT NULL,
    owner_id      INT(4) UNSIGNED NOT NULL,
    INDEX(name),
    FOREIGN KEY(owner_id) REFERENCES owner(id),
    FOREIGN KEY(type_id) REFERENCES type(id)
) engine=InnoDB;

CREATE TABLE IF NOT EXISTS visit (
     id            INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
     pet_id        INT(4) UNSIGNED NOT NULL,
     date          DATE,
     description   VARCHAR(255),
     FOREIGN KEY(pet_id) REFERENCES pet(id)
) engine=InnoDB;