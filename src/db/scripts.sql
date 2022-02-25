CREATE TABLE item
(
    id      SERIAL PRIMARY KEY,
    descr   text,
    created timestamp DEFAULT current_timestamp,
    done    boolean   DEFAULT false
);

create table j_user
(
    id   serial primary key,
    name varchar(20) unique,
    password varchar(20) unique
);

create table user_item
(

);