/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2011/5/10 22:52:47                           */
/*==============================================================*/


drop table if exists blog;

drop table if exists category;

drop table if exists comment;

drop table if exists users;

/*==============================================================*/
/* Table: blog                                                  */
/*==============================================================*/
create table blog
(
   id                   int not null,
   categoryid           int,
   title                varchar(400),
   content              varchar(4000),
   created_time         datetime,
   primary key (id)
);

/*==============================================================*/
/* Table: category                                              */
/*==============================================================*/
create table category
(
   id                   int not null,
   name                 varchar(20),
   level                int,
   primary key (id)
);

/*==============================================================*/
/* Table: comment                                               */
/*==============================================================*/
create table comment
(
   id                   int not null,
   blogid               int,
   username             varchar(200),
   content              varchar(1000),
   primary key (id)
);

/*==============================================================*/
/* Table: users                                                 */
/*==============================================================*/
create table users
(
   id                   int not null,
   username             varchar(200),
   password             varchar(200),
   primary key (id)
);

alter table blog add constraint FK_Relationship_1 foreign key (categoryid)
      references category (id) on delete restrict on update restrict;

alter table comment add constraint FK_Relationship_2 foreign key (blogid)
      references blog (id) on delete restrict on update restrict;

