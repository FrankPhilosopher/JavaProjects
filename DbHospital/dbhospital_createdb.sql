/*==============================================================*/
/* Database name:  dbhospital                                   */
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2013-01-10 ÐÇÆÚËÄ 16:36:21                      */
/*==============================================================*/


drop database dbhospital
go

/*==============================================================*/
/* Database: dbhospital                                         */
/*==============================================================*/
create database dbhospital
go

use dbhospital
go

/*==============================================================*/
/* Table: t_arrangement                                         */
/*==============================================================*/
create table t_arrangement (
   id                   int                  identity,
   doctor_id            int                  null,
   room_id              int                  null,
   expense              float                null,
   limit                int                  null,
   date                 int                  null,
   "order"              int                  null,
   constraint PK_T_ARRANGEMENT primary key (id)
)
go

/*==============================================================*/
/* Table: t_card                                                */
/*==============================================================*/
create table t_card (
   id                   int                  identity,
   cardnum              varchar(32)          null,
   user_id              int                  null,
   worker_id            int                  null,
   datetime             datetime             null default getdate(),
   dealed               int                  null,
   constraint PK_T_CARD primary key (id)
)
go

/*==============================================================*/
/* Table: t_directory                                           */
/*==============================================================*/
create table t_directory (
   id                   int                  identity,
   name                 varchar(128)         null,
   value                varchar(256)         null,
   constraint PK_T_DIRECTORY primary key (id)
)
go

/*==============================================================*/
/* Table: t_doctor                                              */
/*==============================================================*/
create table t_doctor (
   id                   int                  identity,
   name                 varchar(32)          null,
   gander               int                  null,
   title                varchar(32)          null,
   phone                varchar(32)          null,
   info                 varchar(256)         null,
   regtime              datetime             null default getdate(),
   room_id              int                  null,
   constraint PK_T_DOCTOR primary key (id)
)
go

/*==============================================================*/
/* Table: t_register                                            */
/*==============================================================*/
create table t_register (
   id                   int                  not null,
   arrangement_id       int                  null,
   card_id              int                  null,
   number               varchar(32)          null,
   type                 int                  null,
   validate             int                  null,
   constraint PK_T_REGISTER primary key (id)
)
go

/*==============================================================*/
/* Table: t_room                                                */
/*==============================================================*/
create table t_room (
   id                   int                  identity,
   name                 varchar(128)         null,
   constraint PK_T_ROOM primary key (id)
)
go

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user (
   id                   int                  not null,
   realname             varchar(32)          null,
   gander               int                  null,
   phone                varchar(32)          null,
   card                 varchar(32)          null,
   regtime              datetime             null default getdate(),
   name                 varchar(32)          null,
   password             varchar(32)          null,
   card_id              int                  null,
   constraint PK_T_USER primary key (id)
)
go

/*==============================================================*/
/* Table: t_worker                                              */
/*==============================================================*/
create table t_worker (
   id                   int                  identity,
   realname             varchar(32)          null,
   name                 varchar(32)          null,
   password             varchar(32)          null,
   gander               int                  null,
   phone                varchar(32)          null,
   regtime              datetime             null default getdate(),
   canStatics           int                  null,
   canModify            int                  null,
   type                 int                  not null,
   constraint PK_T_WORKER primary key (id)
)
go

alter table t_arrangement
   add constraint FK_T_ARRANG_REFERENCE_T_DOCTOR foreign key (doctor_id)
      references t_doctor (id)
go

alter table t_arrangement
   add constraint FK_T_ARRANG_REFERENCE_T_ROOM foreign key (room_id)
      references t_room (id)
go

alter table t_card
   add constraint FK_T_CARD_REFERENCE_T_USER foreign key (user_id)
      references t_user (id)
go

alter table t_card
   add constraint FK_T_CARD_REFERENCE_T_WORKER foreign key (worker_id)
      references t_worker (id)
go

alter table t_doctor
   add constraint FK_T_DOCTOR_REFERENCE_T_ROOM foreign key (room_id)
      references t_room (id)
go

alter table t_register
   add constraint FK_T_REGIST_REFERENCE_T_ARRANG foreign key (arrangement_id)
      references t_arrangement (id)
go

alter table t_register
   add constraint FK_T_REGIST_REFERENCE_T_CARD foreign key (card_id)
      references t_card (id)
go

alter table t_user
   add constraint FK_T_USER_REFERENCE_T_CARD foreign key (card_id)
      references t_card (id)
go

