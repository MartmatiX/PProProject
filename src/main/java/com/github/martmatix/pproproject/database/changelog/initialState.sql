drop database if exists ppro_project;

create database ppro_project;

use ppro_project;

create table user(
    id int auto_increment not null primary key,
    name varchar(64),
    surname varchar(64),
    email varchar(255),
    username varchar(64),
    password varchar(255)
)