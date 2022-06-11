create type main.operation_type     as enum ('BASE', 'TRANS', 'INCOME', 'EXPENSE', 'EXCHANGE');
create type main.currency           as enum ('RUB', 'USD', 'EUR');


create table main.accounts (
    id          bigserial               not null,
    title       varchar(100)            not null,
    currency    main.currency           not null,
    balance     numeric(15, 2)          not null    default 0,
    is_closed   bool                    not null    default false,

    constraint  accounts_pkey           primary key (id),
    constraint  accounts_title_key      unique (title)
);


create table main.categories (
    id          bigserial               not null,
    parent_id   int8                    null,
    title       varchar(100)            not null,

    constraint  categories_pkey             primary key (id),
    constraint  categories_parent_id_fkey   foreign key (parent_id) references main.categories(id)
);


create table main.operations (
    id              bigserial           not null,
    op_date         date                not null,
    account_id      int8                not null,
    quantity        numeric(15, 6)      not null,
    amount          numeric(15, 2)      not null,
    op_type         main.operation_type not null,
    subcategory_id  int8                null,
    counterparty    varchar(100)        null,
    "comment"       varchar(100)        null,

    constraint operations_pkey              primary key (id),
    constraint operations_account_id_fkey   foreign key (account_id)  references main.accounts(id),
    constraint operations_category_id_fkey  foreign key (subcategory_id) references main.categories(id)
);
