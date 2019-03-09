# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  constraint pk_category primary key (id)
);

create table item_on_sale (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  description                   varchar(255),
  stock                         integer not null,
  price                         double not null,
  category_id                   bigint,
  constraint pk_item_on_sale primary key (id)
);

create table user (
  type                          varchar(31) not null,
  email                         varchar(255) not null,
  date_of_birth                 date,
  name                          varchar(255),
  password                      varchar(255),
  role                          varchar(255),
  street1                       varchar(255),
  street2                       varchar(255),
  town                          varchar(255),
  post_code                     varchar(255),
  credit_card                   varchar(255),
  constraint pk_user primary key (email)
);

alter table item_on_sale add constraint fk_item_on_sale_category_id foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_item_on_sale_category_id on item_on_sale (category_id);


# --- !Downs

alter table item_on_sale drop constraint if exists fk_item_on_sale_category_id;
drop index if exists ix_item_on_sale_category_id;

drop table if exists category;

drop table if exists item_on_sale;

drop table if exists user;

