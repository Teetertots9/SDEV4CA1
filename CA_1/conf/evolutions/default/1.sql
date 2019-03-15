# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table address (
  address_id                    bigint auto_increment not null,
  street1                       varchar(255),
  street2                       varchar(255),
  town                          varchar(255),
  post_code                     varchar(255),
  employee_email                varchar(255),
  constraint uq_address_employee_email unique (employee_email),
  constraint pk_address primary key (address_id)
);

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

create table department (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  constraint pk_department primary key (id)
);

create table item_on_sale (
  id                            bigint auto_increment not null,
  name                          varchar(255),
  description                   varchar(255),
  stock                         integer not null,
  price                         double not null,
  constraint pk_item_on_sale primary key (id)
);

create table project (
  proj_name                     varchar(255) not null,
  constraint pk_project primary key (proj_name)
);

create table user (
  type                          varchar(31) not null,
  email                         varchar(255) not null,
  name                          varchar(255),
  password                      varchar(255),
  role                          varchar(255),
  department_id                 bigint,
  constraint pk_user primary key (email)
);

create table user_project (
  user_email                    varchar(255) not null,
  project_proj_name             varchar(255) not null,
  constraint pk_user_project primary key (user_email,project_proj_name)
);

alter table address add constraint fk_address_employee_email foreign key (employee_email) references user (email) on delete restrict on update restrict;

alter table category_item_on_sale add constraint fk_category_item_on_sale_category foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_category_item_on_sale_category on category_item_on_sale (category_id);

alter table category_item_on_sale add constraint fk_category_item_on_sale_item_on_sale foreign key (item_on_sale_id) references item_on_sale (id) on delete restrict on update restrict;
create index ix_category_item_on_sale_item_on_sale on category_item_on_sale (item_on_sale_id);

alter table user add constraint fk_user_department_id foreign key (department_id) references department (id) on delete restrict on update restrict;
create index ix_user_department_id on user (department_id);

alter table user_project add constraint fk_user_project_user foreign key (user_email) references user (email) on delete restrict on update restrict;
create index ix_user_project_user on user_project (user_email);

alter table user_project add constraint fk_user_project_project foreign key (project_proj_name) references project (proj_name) on delete restrict on update restrict;
create index ix_user_project_project on user_project (project_proj_name);


# --- !Downs

alter table address drop constraint if exists fk_address_employee_email;

alter table category_item_on_sale drop constraint if exists fk_category_item_on_sale_category;
drop index if exists ix_category_item_on_sale_category;

alter table category_item_on_sale drop constraint if exists fk_category_item_on_sale_item_on_sale;
drop index if exists ix_category_item_on_sale_item_on_sale;

alter table user drop constraint if exists fk_user_department_id;
drop index if exists ix_user_department_id;

alter table user_project drop constraint if exists fk_user_project_user;
drop index if exists ix_user_project_user;

alter table user_project drop constraint if exists fk_user_project_project;
drop index if exists ix_user_project_project;

drop table if exists address;

drop table if exists category;

drop table if exists category_item_on_sale;

drop table if exists department;

drop table if exists item_on_sale;

drop table if exists project;

drop table if exists user;

drop table if exists user_project;

