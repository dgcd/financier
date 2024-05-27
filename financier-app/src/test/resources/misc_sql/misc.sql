select version();


select c.id, p.title as parent, c.title as category, count(*) as "count"
from main.categories c
join main.categories p on p.id = c.parent_id
join main.operations o on o.subcategory_id = c.id
where o.is_canceled is false
group by p.title, c.title, c.id
order by p.title, c.title;


    select cc.title, c.title, sum(o.amount)
    from main.operations o
    join main.categories c
        on o.subcategory_id = c.id
    join main.categories cc
        on c.parent_id = cc.id
    where o."comment" = 'шерегеш-24'
        and o.is_canceled = false
    group by cc.title,  c.title
union
    select 'Total', 'Total', sum(o.amount)
    from main.operations o
    where o."comment" = 'шерегеш-24'
        and o.is_canceled = false
;


select *
from main.operations o
where o.subcategory_id = 44;



select * from main.operations
where account_id = 6
--where subcategory_id in (24, 28)
order by op_date desc;

update main.operations
set account_id = 6 where account_id = 7;


