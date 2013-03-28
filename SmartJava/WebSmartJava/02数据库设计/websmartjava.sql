/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2005                    */
/* Created on:     2012-9-11 14:00:40                           */
/*==============================================================*/


if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('car') and o.name = 'FK_CAR_REFERENCE_DISTRIBU4')
alter table car
   drop constraint FK_CAR_REFERENCE_DISTRIBU4
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('distributionPrice') and o.name = 'FK_DISTRIBU_REFERENCE_DISTRIBU9')
alter table distributionPrice
   drop constraint FK_DISTRIBU_REFERENCE_DISTRIBU9
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('distributionPrice') and o.name = 'FK_DISTRIBU_REFERENCE_DISTRIBU8')
alter table distributionPrice
   drop constraint FK_DISTRIBU_REFERENCE_DISTRIBU8
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('orders') and o.name = 'FK_ORDERS_REFERENCE_DISTRIBU7')
alter table orders
   drop constraint FK_ORDERS_REFERENCE_DISTRIBU7
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('orders') and o.name = 'FK_ORDERS_REFERENCE_CAR5')
alter table orders
   drop constraint FK_ORDERS_REFERENCE_CAR5
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('orders') and o.name = 'FK_ORDERS_REFERENCE_DISTRIBU6')
alter table orders
   drop constraint FK_ORDERS_REFERENCE_DISTRIBU6
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('orders') and o.name = 'FK_ORDERS_REFERENCE_DISTRIBU10')
alter table orders
   drop constraint FK_ORDERS_REFERENCE_DISTRIBU10
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('orders') and o.name = 'FK_ORDERS_REFERENCE_USER11')
alter table orders
   drop constraint FK_ORDERS_REFERENCE_USER11
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('passingSheet') and o.name = 'FK_PASSINGS_REFERENCE_ORDERS1')
alter table passingSheet
   drop constraint FK_PASSINGS_REFERENCE_ORDERS1
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('passingSheet') and o.name = 'FK_PASSINGS_REFERENCE_CAR2')
alter table passingSheet
   drop constraint FK_PASSINGS_REFERENCE_CAR2
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('passingSheet') and o.name = 'FK_PASSINGS_REFERENCE_DISTRIBU3')
alter table passingSheet
   drop constraint FK_PASSINGS_REFERENCE_DISTRIBU3
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('placePrice') and o.name = 'FK_PLACEPRI_REFERENCE_DISTRIBU12')
alter table placePrice
   drop constraint FK_PLACEPRI_REFERENCE_DISTRIBU12
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('route') and o.name = 'FK_ROUTE_REFERENCE_DISTRIBU13')
alter table route
   drop constraint FK_ROUTE_REFERENCE_DISTRIBU13
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('route') and o.name = 'FK_ROUTE_REFERENCE_DISTRIBU14')
alter table route
   drop constraint FK_ROUTE_REFERENCE_DISTRIBU14
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('"user"') and o.name = 'FK_USER_REFERENCE_COMPANY15')
alter table "user"
   drop constraint FK_USER_REFERENCE_COMPANY15
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('"user"') and o.name = 'FK_USER_REFERENCE_DISTRIBU16')
alter table "user"
   drop constraint FK_USER_REFERENCE_DISTRIBU16
go

if exists (select 1
            from  sysobjects
           where  id = object_id('car')
            and   type = 'U')
   drop table car
go

if exists (select 1
            from  sysobjects
           where  id = object_id('company')
            and   type = 'U')
   drop table company
go

if exists (select 1
            from  sysobjects
           where  id = object_id('distribution')
            and   type = 'U')
   drop table distribution
go

if exists (select 1
            from  sysobjects
           where  id = object_id('distributionPrice')
            and   type = 'U')
   drop table distributionPrice
go

if exists (select 1
            from  sysobjects
           where  id = object_id('orders')
            and   type = 'U')
   drop table orders
go

if exists (select 1
            from  sysobjects
           where  id = object_id('passingSheet')
            and   type = 'U')
   drop table passingSheet
go

if exists (select 1
            from  sysobjects
           where  id = object_id('placePrice')
            and   type = 'U')
   drop table placePrice
go

if exists (select 1
            from  sysobjects
           where  id = object_id('route')
            and   type = 'U')
   drop table route
go

if exists (select 1
            from  sysobjects
           where  id = object_id('"user"')
            and   type = 'U')
   drop table "user"
go

/*==============================================================*/
/* Table: car                                                   */
/*==============================================================*/
create table car (
   id                   int                  identity,
   number               varchar(64)          not null,
   driver               varchar(64)          not null,
   weight               float                not null,
   volumn               float                not null,
   currentDistribution  int                  null,
   status               int                  not null,
   constraint PK_CAR primary key (id)
)
go

/*==============================================================*/
/* Table: company                                               */
/*==============================================================*/
create table company (
   id                   int                  identity,
   name                 varchar(64)          not null,
   constraint PK_COMPANY primary key (id)
)
go

/*==============================================================*/
/* Table: distribution                                          */
/*==============================================================*/
create table distribution (
   id                   int                  identity,
   name                 varchar(64)          not null,
   address              varchar(256)         not null,
   constraint PK_DISTRIBUTION primary key (id)
)
go

/*==============================================================*/
/* Table: distributionPrice                                     */
/*==============================================================*/
create table distributionPrice (
   id                   int                  identity,
   sDistribution        int                  not null,
   tDistribution        int                  not null,
   firstKilo            float                not null,
   secondKilo           float                not null,
   constraint PK_DISTRIBUTIONPRICE primary key (id)
)
go

/*==============================================================*/
/* Table: orders                                                */
/*==============================================================*/
create table orders (
   id                   int                  identity,
   userId               int                  null,
   sender               varchar(64)          not null,
   sAddress             varchar(256)         not null,
   sCode                varchar(64)          not null,
   sPhone               varchar(64)          not null,
   sDistribution        int                  not null,
   receiver             varchar(64)          not null,
   tAddress             varchar(256)         not null,
   tCode                varchar(64)          not null,
   tPhone               varchar(64)          not null,
   tDistribution        int                  not null,
   worker               varchar(64)          null,
   workDate             datetime             null,
   volumn               float                not null,
   weight               float                not null,
   path                 varchar(1024)        not null,
   price                float                not null,
   status               int                  not null,
   carId                int                  null,
   currentDistribution  int                  null,
   goodsType            int                  null,
   remark               varchar(256)         null,
   constraint PK_ORDERS primary key (id)
)
go

/*==============================================================*/
/* Table: passingSheet                                          */
/*==============================================================*/
create table passingSheet (
   id                   int                  identity,
   orderId              int                  not null,
   carId                int                  not null,
   distributionId       int                  not null,
   date                 datetime             not null,
   constraint PK_PASSINGSHEET primary key (id)
)
go

/*==============================================================*/
/* Table: placePrice                                            */
/*==============================================================*/
create table placePrice (
   id                   int                  identity,
   distributionId       int                  not null,
   placeName            varchar(64)          not null,
   placePrice           float                not null,
   placeTime            varchar(64)          not null,
   remark               varchar(256)         null,
   constraint PK_PLACEPRICE primary key (id)
)
go

/*==============================================================*/
/* Table: route                                                 */
/*==============================================================*/
create table route (
   id                   int                  identity,
   sDistribution        int                  not null,
   tDistribution        int                  not null,
   name                 varchar(64)          not null,
   length               float                not null,
   constraint PK_ROUTE primary key (id)
)
go

/*==============================================================*/
/* Table: "user"                                                */
/*==============================================================*/
create table "user" (
   id                   int                  identity,
   name                 varchar(64)          not null,
   password             varchar(64)          not null,
   realname             varchar(64)          not null,
   phone                varchar(64)          null,
   distributionId       int                  null,
   companyId            int                  null,
   priority             int                  not null,
   constraint PK_USER primary key (id)
)
go

alter table car
   add constraint FK_CAR_REFERENCE_DISTRIBU4 foreign key (currentDistribution)
      references distribution (id)
go

alter table distributionPrice
   add constraint FK_DISTRIBU_REFERENCE_DISTRIBU9 foreign key (sDistribution)
      references distribution (id)
go

alter table distributionPrice
   add constraint FK_DISTRIBU_REFERENCE_DISTRIBU8 foreign key (tDistribution)
      references distribution (id)
go

alter table orders
   add constraint FK_ORDERS_REFERENCE_DISTRIBU7 foreign key (tDistribution)
      references distribution (id)
go

alter table orders
   add constraint FK_ORDERS_REFERENCE_CAR5 foreign key (carId)
      references car (id)
go

alter table orders
   add constraint FK_ORDERS_REFERENCE_DISTRIBU6 foreign key (currentDistribution)
      references distribution (id)
go

alter table orders
   add constraint FK_ORDERS_REFERENCE_DISTRIBU10 foreign key (sDistribution)
      references distribution (id)
go

alter table orders
   add constraint FK_ORDERS_REFERENCE_USER11 foreign key (userId)
      references "user" (id)
go

alter table passingSheet
   add constraint FK_PASSINGS_REFERENCE_ORDERS1 foreign key (orderId)
      references orders (id)
go

alter table passingSheet
   add constraint FK_PASSINGS_REFERENCE_CAR2 foreign key (carId)
      references car (id)
go

alter table passingSheet
   add constraint FK_PASSINGS_REFERENCE_DISTRIBU3 foreign key (distributionId)
      references distribution (id)
go

alter table placePrice
   add constraint FK_PLACEPRI_REFERENCE_DISTRIBU12 foreign key (distributionId)
      references distribution (id)
go

alter table route
   add constraint FK_ROUTE_REFERENCE_DISTRIBU13 foreign key (tDistribution)
      references distribution (id)
go

alter table route
   add constraint FK_ROUTE_REFERENCE_DISTRIBU14 foreign key (sDistribution)
      references distribution (id)
go

alter table "user"
   add constraint FK_USER_REFERENCE_COMPANY15 foreign key (companyId)
      references company (id)
go

alter table "user"
   add constraint FK_USER_REFERENCE_DISTRIBU16 foreign key (distributionId)
      references distribution (id)
go

