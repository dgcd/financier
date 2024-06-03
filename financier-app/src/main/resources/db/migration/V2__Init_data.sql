-- todo: remove this file

INSERT INTO main.accounts (title,currency,balance,closed) VALUES
	 ('test RUB','RUB',6999.20,false),
	 ('closed RUB','RUB',0.00,true),
	 ('test usd','USD',0.00,false);

INSERT INTO main.categories (title,parent_id) VALUES
	 ('parent1',NULL),
	 ('child1',1);

INSERT INTO main.operations ("date",account_id,quantity,amount,"type",subcategory_id,"comment",counterparty,correlation_id,canceled) VALUES
	 ('2024-05-10',1,4.000000,-2000.40,'EXPENSE',2,'commetn2','cntpty',NULL,false),
	 ('2024-05-10',1,2.000000,-1000.20,'EXPENSE',2,'commetn3','cntpty',NULL,true),
	 ('2024-05-10',1,2.000000,-1000.40,'EXPENSE',2,'commetn1','cntpty',NULL,false),
	 ('2024-05-09',1,1.000000,10000.00,'BASE',2,'commetn1','cntpty',NULL,false);

INSERT INTO main.rates ("date",usd,eur) VALUES
	 ('2024-05-01',90.50,100.20),
	 ('2024-05-02',91.50,101.20),
	 ('2024-05-03',92.50,102.20);
