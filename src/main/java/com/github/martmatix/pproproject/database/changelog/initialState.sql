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
    password varchar(255),
    enabled  boolean,
    role     varchar(64)
);

insert into user(name,
                 surname,
                 email,
                 username,
                 password,
                 enabled,
                 role)
values ("Martin", "Malir", "malirma1@uhk.cz", "martmatix",
        "$2a$10$zJrpOcdVz9VWz3YocZrl/.PdoJGduKtk4v0IZmldHbAO3FzH5hI5K", true, "USER");

insert into user(name,
                 surname,
                 email,
                 username,
                 password,
                 enabled,
                 role)
values ("admin", "admin", "admin@gmail.com", "admin",
        "$2a$10$zJrpOcdVz9VWz3YocZrl/.PdoJGduKtk4v0IZmldHbAO3FzH5hI5K", true, "ADMINISTRATOR");

create table announcement
(
    id             int auto_increment not null primary key,
    admin_username varchar(64),
    title          varchar(255),
    message        varchar(2048)
);

insert into announcement (admin_username, title, message)
VALUES ("admin", "Title 1", "This is a testing announcement 1"),
       ("admin", "Title 2", "This is a testing announcement 2"),
       ("admin", "Title 3", "This is a testing announcement 3"),
       ("admin", "Title 4", "This is a testing announcement 4"),
       ("admin", "Title 5", "This is a testing announcement 5");