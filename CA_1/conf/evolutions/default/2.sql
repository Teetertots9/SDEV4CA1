# --- Sample dataset
 
# --- !Ups
 
delete from item_on_sale;
delete from category;
delete from category_item_on_sale;
 
insert into category (id,name) values ( 1,'Electrical and Electronics' );
insert into category (id,name) values ( 2,'Books' );
insert into category (id,name) values ( 3,'Clothes' );
insert into category (id,name) values ( 4,'Household' );
insert into category (id,name) values ( 5,'Musical Instruments' );
insert into category (id,name) values ( 6,'Sports Equipment' );
 
insert into item_on_sale (id,name,description,stock,price) values ( 1,'Television','Sony 42" LCD',1, 100.00 );
insert into item_on_sale (id,name,description,stock,price) values ( 2,'Book','Mysteries of the Universe',1,3.00 );
insert into item_on_sale (id,name,description,stock,price) values ( 3,'Fluffy Socks','Warm extra long socks (new)',100,2.00 );
insert into item_on_sale (id,name,description,stock,price) values ( 4,'Tumbler Glass','Reject tumbler glasses (new)',40,1.00 );
insert into item_on_sale (id,name,description,stock,price) values ( 5,'Piano','Grand, in need of tuning',1,99.00 );
insert into item_on_sale (id,name,description,stock,price) values ( 6,'Chair','Comfortable armchair in good condition',1,50.00 );
insert into item_on_sale (id,name,description,stock,price) values ( 7,'Washing Machine','1600rpm spin, A+++ rated, 10KG',2,300.00 );
insert into item_on_sale (id,name,description,stock,price) values ( 8,'Water jug','Antique cristal, 2l',2,75.00 );
insert into item_on_sale (id,name,description,stock,price) values ( 9,'Rowing machine','Great for keeping fit',1,50.00 );
insert into item_on_sale (id,name,description,stock,price) values ( 10,'Junior tennis raquet','Never used',2,15.00 );

insert into CATEGORY_ITEM_ON_SALE(category_id,item_on_sale_id) values (1,1);
insert into CATEGORY_ITEM_ON_SALE(category_id,item_on_sale_id) values (2,2);
insert into CATEGORY_ITEM_ON_SALE(category_id,item_on_sale_id) values (3,3);
insert into CATEGORY_ITEM_ON_SALE(category_id,item_on_sale_id) values (4,4);
insert into CATEGORY_ITEM_ON_SALE(category_id,item_on_sale_id) values (5,5);
insert into CATEGORY_ITEM_ON_SALE(category_id,item_on_sale_id) values (4,6);
insert into CATEGORY_ITEM_ON_SALE(category_id,item_on_sale_id) values (4,7);
insert into CATEGORY_ITEM_ON_SALE(category_id,item_on_sale_id) values (4,8);
insert into CATEGORY_ITEM_ON_SALE(category_id,item_on_sale_id) values (6,9);
insert into CATEGORY_ITEM_ON_SALE(category_id,item_on_sale_id) values (6,10);