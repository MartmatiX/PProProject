drop database if exists ppro_project;

create database ppro_project;

use ppro_project;

create table user
(
    id       int auto_increment not null primary key,
    name     varchar(64),
    surname  varchar(64),
    email    varchar(255),
    username varchar(64) unique,
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
    message        varchar(2048),
    foreign key (admin_username)
        references user (username)
        on delete cascade
);

insert into announcement (admin_username, title, message)
VALUES ("admin", "Title 1", "This is a testing announcement 1"),
       ("admin", "Title 2", "This is a testing announcement 2"),
       ("admin", "Title 3", "This is a testing announcement 3"),
       ("admin", "Title 4", "This is a testing announcement 4"),
       ("admin", "Title 5", "This is a testing announcement 5");

create table message
(
    id            int auto_increment not null primary key,
    sender_name   varchar(64),
    receiver_name varchar(64),
    message       varchar(1024),
    foreign key (sender_name)
        references user (username)
        on delete cascade,
    foreign key (receiver_name)
        references user (username)
        on delete cascade
);

insert into message (sender_name, receiver_name, message)
values ("admin", "martmatix", "Hello!"),
       ("martmatix", "admin", "Hi"),
       ("admin", "martmatix", "What are you doing?"),
       ("martmatix", "admin", "Testing the messaging system"),
       ("admin", "martmatix", "Does it work?"),
       ("martmatix", "admin", "Without web socket, but yes, it does");

create table record_type
(
    id          int auto_increment not null primary key,
    name        varchar(64) unique,
    description varchar(255),
    issuer      varchar(64),
    foreign key (issuer)
        references user (username)
        on update cascade
);

insert into record_type (name, description, issuer)
values ("TICKET-01", "This is a test ticket", "admin"),
       ("TICKET-02", "This is a test ticket number 2", "admin");

create table record
(
    id             int auto_increment not null primary key,
    date           date,
    user           varchar(64),
    approved       boolean,
    message        varchar(255),
    length         int,
    record_type_id int,
    foreign key (user)
        references user (username)
        on update cascade,
    foreign key (record_type_id)
        references record_type (id)
        on update cascade
);

insert into record(date, user, approved, message, length, record_type_id)
values ("2024-10-05", "admin", 1, "Updated something", 100, 1),
       ("2024-10-05", "admin", 1, "Added something", 100, 2),
       ("2024-10-05", "admin", 0, "Not approved yet", 50, 1);
