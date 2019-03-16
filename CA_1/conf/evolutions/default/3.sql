# --- !Ups

delete from user;
delete from department;

insert into department(id,name) values (1,'IT');
insert into department(id,name) values (2,'Finance');
insert into department(id,name) values (3,'HR');
insert into department(id,name) values (4,'Marketing');



insert into user (type,email,role,name,password) values ( 'm','manager@ofm.com','manager','Mike Manager', 'password');
 