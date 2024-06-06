insert into main.accounts (id, title, currency, balance, closed) values
    (1, 'test test RUB', 'RUB', 1000, false);

INSERT INTO main.categories (id, parent_id, title) VALUES
    (1, NULL,'transport'),
    (2, 1,'fuel');

INSERT INTO main.operations (id, "date",account_id,quantity,amount,"type",subcategory_id,"comment",counterparty,canceled,correlation_id) VALUES
    (1, '2022-09-08',1, 1.000000, 10000.00,'BASE',NULL,NULL,NULL,false,NULL),
    (2, '2022-09-09',1, 1.000000, -100.00,'EXPENSE',2,'fuel','fuel',false,NULL),
    (3, '2022-09-09',1, 1.000000, -200.00,'EXPENSE',2,'fuel','fuel',false,NULL);

INSERT INTO main.rates ("date", eur, usd) VALUES
    ('2022-09-08', 110, 100);
