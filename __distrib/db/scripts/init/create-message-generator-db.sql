drop table if exists MESSAGE_GENERATOR.DB_CONNECTIONS;
drop table if exists MESSAGE_GENERATOR.LOCAL_PATHS;

drop schema if exists MESSAGE_GENERATOR;

create schema if not exists MESSAGE_GENERATOR;

create table if not exists MESSAGE_GENERATOR.DB_CONNECTIONS (
    id int auto_increment primary key,
    name varchar(200) not null,
    url varchar(300) not null,
    driverclassname varchar(200) not null,
    user varchar(200) not null,
    password varchar(200) not null,
    active boolean default false not null
);

create table if not exists MESSAGE_GENERATOR.LOCAL_PATHS (
    id int auto_increment primary key,
    name varchar(200) not null,
    description varchar(300) not null,
    path varchar(300) not null
);

create table if not exists MESSAGE_GENERATOR.GUI_THEMES (
   id int auto_increment primary key,
   name varchar(100) not null,
   active boolean default false not null
);

insert into MESSAGE_GENERATOR.GUI_THEMES
(name, active)
values
('Metal', false),
('Nimbus', true),
('Windows', false);
