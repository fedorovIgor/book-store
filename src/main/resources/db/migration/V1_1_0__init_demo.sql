
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    role_name VARCHAR(64) UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(64) UNIQUE,
    password VARCHAR(128),
    role_id INT REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS authority (
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) UNIQUE,
    description VARCHAR
);

CREATE TABLE IF NOT EXISTS role_authority (
    role_id INT REFERENCES roles(id),
    authority_id INT REFERENCES authority(id)
);



--
--  CREATE SCHEMA FOR BookBucket --
--DROP TABLE IF EXISTS title_genre;
--DROP TABLE IF EXISTS title_author;
--DROP TABLE IF EXISTS book;
--DROP TABLE IF EXISTS author;
--DROP TABLE IF EXISTS genre;
--DROP TABLE IF EXISTS title;

CREATE TABLE IF NOT EXISTS title (
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) UNIQUE,
    date_writing DATE,
    image_url VARCHAR
);

CREATE TABLE IF NOT EXISTS genre (
    id SERIAL PRIMARY KEY,
    genre_name VARCHAR(64) UNIQUE
 );

 CREATE TABLE IF NOT EXISTS author (
     id SERIAL PRIMARY KEY,
     first_name VARCHAR(64),
     last_name VARCHAR(64),
     birthday DATE,
     biography VARCHAR
 );

CREATE TABLE IF NOT EXISTS book (
    id SERIAL PRIMARY KEY,
    date_publication DATE,
    publisher VARCHAR(64),
    price INT,
    download_url VARCHAR,
    title_fk INT REFERENCES title (id)
 );


 CREATE TABLE IF NOT EXISTS title_author (
     author_id INT REFERENCES author(id),
     title_id INT REFERENCES title(id)
 );

 CREATE TABLE IF NOT EXISTS title_genre (
     genre_id INT REFERENCES genre(id),
     title_id INT REFERENCES title(id)
 );

 CREATE TABLE IF NOT EXISTS user_resource (
    id int PRIMARY KEY,
    username VARCHAR NOT NULL,
    book_id INT NOT NULL
)



