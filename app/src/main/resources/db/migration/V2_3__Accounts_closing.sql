alter table main.accounts add column is_closed bool not null default false;

alter table main.accounts alter column is_closed drop default;
