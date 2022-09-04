alter table main.operations add column is_cancellation bool default false;
update main.operations set is_cancellation = false;
alter table main.operations alter column is_cancellation set not null;
