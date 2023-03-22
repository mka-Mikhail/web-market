create table categories
(
    id         bigserial primary key,
    title      varchar(255) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into categories (title)
values ('Food'),
       ('Others');

create table products
(
    id          bigserial primary key,
    title       varchar(100),
    category_id bigint references categories (id),
    price       int,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

insert into products (title, price, category_id)
values ('Milk', 80, 1),
       ('Bread', 40, 1),
       ('Cheese', 140, 1);

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

create table orders
(
    id          bigserial primary key,
    user_id     bigint not null references users (id),
    total_price int    not null,
    address     varchar(255),
    phone       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table order_items
(
    id                bigserial primary key,
    product_id        bigint not null references products (id),
    order_id          bigint not null references orders (id),
    quantity          int    not null,
    price_per_product int    not null,
    price             int    not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
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