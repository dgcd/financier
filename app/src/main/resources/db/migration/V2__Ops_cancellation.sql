alter table main.operations add column is_canceled bool default false;
update main.operations set is_canceled = false;
alter table main.operations alter column is_canceled set not null;

alter table main.operations add column correlation_id varchar(38) unique;
