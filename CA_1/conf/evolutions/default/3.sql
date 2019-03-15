# --- !Ups

delete from user;

insert into user (type,email,role,name,password) values ( 'm','manager@ofm.com','manager','Mike Manager', 'password');

insert into department(name) values ('IT');