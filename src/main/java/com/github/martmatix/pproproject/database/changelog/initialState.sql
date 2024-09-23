drop database if exists ppro_project;

create database ppro_project;

use ppro_project;

create table user
(
    id       int auto_increment not null primary key,
    name     varchar(64),
    surname  varchar(64),
    email    varchar(255),
    username varchar(64),
    password varchar(255)
);

insert into user(name,
                 surname,
                 email,
                 username,
                 password)
values ("Martin", "Malir", "martin.malir@gmail.com", "martmatix",
        "$2a$10$zJrpOcdVz9VWz3YocZrl/.PdoJGduKtk4v0IZmldHbAO3FzH5hI5K");