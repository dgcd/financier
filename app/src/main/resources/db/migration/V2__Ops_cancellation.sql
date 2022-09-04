alter table main.operations add column is_canceled bool not null default false;
alter table main.operations add column correlation_id varchar(38) unique;
