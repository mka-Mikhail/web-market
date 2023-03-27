create table users
(
    id       bigserial primary key,
    username varchar(36) not null,
    password varchar(80) not null
);

create table roles
(
    id   bigserial primary key,
    name varchar(50) not null
);

create table users_roles
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');
-- 123
-- 123
insert into users (username, password)
values ('Bob', '$2a$12$O5IrykaSJ.DpwlQ/Gwb6ruqXEoXvNNl1TBqNZxRaUqyMZqd/mnFYe'),
       ('John', '$2a$12$O5IrykaSJ.DpwlQ/Gwb6ruqXEoXvNNl1TBqNZxRaUqyMZqd/mnFYe');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);