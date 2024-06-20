insert into accounts (id, title, currency, balance, closed) values
    (1, 'test test RUB', 'RUB', 1000, false);

insert into categories (id, parent_id, title) values
    (1, null,'transport'),
    (2, 1,'fuel');

insert into operations (id, "date",account_id,quantity,amount,"type",subcategory_id,"comment",counterparty,canceled,correlation_id) values
    (1, '2022-09-08',1, 1.000000, 10000.00,'BASE',null,null,null,false,null),
    (2, '2022-09-09',1, 1.000000, -100.00,'EXPENSE',2,'fuel','fuel',false,null),
    (3, '2022-09-09',1, 1.000000, -200.00,'EXPENSE',2,'fuel','fuel',false,null);

insert into rates ("date", eur, usd) values
    ('2022-09-08', 110, 100);
