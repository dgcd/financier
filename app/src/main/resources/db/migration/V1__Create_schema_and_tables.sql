create schema if not exists main;

create type main.operation_type     as enum ('BASE', 'TRANS', 'INCOME', 'EXPENSE');
create type main.currency           as enum ('RUB', 'USD', 'EUR');


create table main.categories (
    id          bigserial           primary key,
    parent_id   bigint              null        references main.categories(id),
    title       varchar(100)        not null    unique,
    op_type     main.operation_type not null
);

create table main.accounts (
    id          bigserial           primary key,
    title       varchar(100)        not null    unique,
    currency    main.currency       not null,
    balance     numeric(15,2)       not null    default 0
);

create table main.operations (
    id          bigserial           primary key,
    account_id  bigint              not null    references main.accounts(id),
    corr_op_id  bigint              null        references main.operations(id),
--    op_type     main.operation_type not null,
    category_id bigint              not null    references main.categories(id),
    amount      numeric(15,2)       not null
);
