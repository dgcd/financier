drop table main.operations2;

create table main.operations (
    id              bigserial           primary key,
    account_id      bigint              not null    references main.accounts(id),
    amount          numeric(15,2)       not null,
    quantity        float4              not null,
    op_type         main.operation_type not null,
    category_id     bigint              not null    references main.categories(id),
    counterparty    varchar(100)        null,
    comment         varchar(100)        null
);
