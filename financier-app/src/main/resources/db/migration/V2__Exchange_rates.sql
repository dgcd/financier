
create table main.rates (
    id_date     date                    not null,
    eur         numeric(10, 2)          not null,
    usd         numeric(10, 2)          not null,

    constraint  rates_pkey              primary key (id_date)
);
