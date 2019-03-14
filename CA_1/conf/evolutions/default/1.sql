# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table category (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  constraint pk_category primary key (id)
);

create table category_item_on_sale (
  category_id                   bigint not null,
  item_on_sale_id               bigint not null,
  constraint pk_category_item_on_sale primary key (category_id,item_on_sale_id)
);

create table item_on_sale (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  description                   varchar(255),
  stock                         integer not null,
  price                         double not null,
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

alter table category_item_on_sale add constraint fk_category_item_on_sale_category foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_category_item_on_sale_category on category_item_on_sale (category_id);

alter table category_item_on_sale add constraint fk_category_item_on_sale_item_on_sale foreign key (item_on_sale_id) references item_on_sale (id) on delete restrict on update restrict;
create index ix_category_item_on_sale_item_on_sale on category_item_on_sale (item_on_sale_id);


# --- !Downs

alter table category_item_on_sale drop constraint if exists fk_category_item_on_sale_category;
drop index if exists ix_category_item_on_sale_category;

alter table category_item_on_sale drop constraint if exists fk_category_item_on_sale_item_on_sale;
drop index if exists ix_category_item_on_sale_item_on_sale;

drop table if exists category;

drop table if exists category_item_on_sale;

drop table if exists item_on_sale;

drop table if exists user;

