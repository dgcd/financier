alter table main.accounts drop column is_closed;
alter table main.accounts add column is_closed bool not null default false;
