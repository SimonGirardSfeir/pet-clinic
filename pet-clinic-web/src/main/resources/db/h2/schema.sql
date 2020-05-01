CREATE TABLE IF NOT EXISTS vet (
    id            INTEGER IDENTITY PRIMARY KEY,
    first_name    VARCHAR(30),
    last_name     VARCHAR(30)
);

CREATE INDEX vet_last_name ON vet(last_name);

CREATE TABLE IF NOT EXISTS speciality (
    id            INTEGER IDENTITY PRIMARY KEY,
    name          VARCHAR(80)
);

CREATE INDEX speciality_name ON speciality(name);

CREATE TABLE IF NOT EXISTS vet_speciality (
    vet_id        INTEGER NOT NULL,
    speciality_id INTEGER NOT NULL
);

ALTER TABLE vet_speciality ADD CONSTRAINT fk_vet_speciality_vet FOREIGN KEY (vet_id) REFERENCES vet(id);
ALTER TABLE vet_speciality ADD CONSTRAINT fk_vet_speciality_speciality FOREIGN KEY (speciality_id) REFERENCES speciality(id);

CREATE TABLE IF NOT EXISTS type (
    id            INTEGER IDENTITY PRIMARY KEY,
    name          VARCHAR(80)
);

CREATE INDEX type_name ON type(name);

CREATE TABLE IF NOT EXISTS owner (
    id            INTEGER IDENTITY PRIMARY KEY,
    first_name    VARCHAR(30),
    last_name     VARCHAR(30),
    address       VARCHAR(255),
    city          VARCHAR(80),
    telephone     VARCHAR(20)
);

CREATE INDEX owner_last_name ON owner(last_name);

CREATE TABLE IF NOT EXISTS pet (
    id            INTEGER IDENTITY PRIMARY KEY,
    name          VARCHAR(80),
    birth_day     DATE,
    type_id       INTEGER NOT NULL,
    owner_id      INTEGER NOT NULL
);

ALTER TABLE pet ADD CONSTRAINT fk_pet_owner FOREIGN KEY (owner_id) REFERENCES owner(id);
ALTER TABLE pet ADD CONSTRAINT fk_pet_type FOREIGN KEY (type_id) REFERENCES type(id);
CREATE INDEX pet_name ON pet(name);

CREATE TABLE IF NOT EXISTS visit (
    id            INTEGER IDENTITY PRIMARY KEY,
    pet_id        INTEGER NOT NULL,
    date          DATE,
    description   VARCHAR(255)
);

ALTER TABLE visit ADD CONSTRAINT fk_visit_pet FOREIGN KEY (pet_id) REFERENCES pet(id);
CREATE INDEX visit_pet_id ON visit(pet_id);

