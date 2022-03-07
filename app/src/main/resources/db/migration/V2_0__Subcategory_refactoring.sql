alter table main.operations drop column category_id;
alter table main.operations add  column subcategory_id int8 null;
