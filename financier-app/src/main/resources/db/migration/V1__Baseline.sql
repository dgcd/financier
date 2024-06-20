create type operation_type      as enum ('BASE', 'TRANS', 'INCOME', 'EXPENSE', 'EXCHANGE');
create type currency_type       as enum ('RUB', 'USD', 'EUR');


create table accounts (
    id          bigserial               not null,
    title       varchar(40)             not null,
    currency    currency_type           not null,
    balance     numeric(15, 2)          not null    default 0,
    closed      bool                    not null    default false,

    constraint  accounts_pkey           primary key (id),
    constraint  accounts_title_ukey     unique (title)
);


create table categories (
    id          bigserial               not null,
    title       varchar(30)             not null,
    parent_id   int8                    null,

    constraint  categories_pkey                 primary key (id),
    constraint  categories_parent_id_fkey       foreign key (parent_id) references categories(id),
    constraint  categories_title_parent_id_ukey unique nulls not distinct (title, parent_id)
);


create table operations (
    id              bigserial           not null,
    "date"          date                not null,
    account_id      int8                not null,
    quantity        numeric(15, 6)      not null,
    amount          numeric(15, 2)      not null,
    "type"          operation_type      not null,
    subcategory_id  int8                null,
    "comment"       varchar(30)         null,
    counterparty    varchar(30)         null,
    correlation_id  varchar(20)         null,
    canceled        bool                not null    default false,

    constraint operations_pkey                  primary key (id),
    constraint operations_account_id_fkey       foreign key (account_id)  references accounts(id),
    constraint operations_category_id_fkey      foreign key (subcategory_id) references categories(id),
    constraint operations_correlation_id_ukey   unique (correlation_id),
    constraint operations_positive_quantity     check (quantity > 0)
);


create table rates (
    "date"      date                    not null,
    usd         numeric(10, 2)          not null,
    eur         numeric(10, 2)          not null,

    constraint  rates_pkey              primary key (date),
    constraint  rates_positive_usd      check (usd > 0),
    constraint  rates_positive_eur      check (eur > 0)
);
