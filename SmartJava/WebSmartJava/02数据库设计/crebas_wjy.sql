/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2005                    */
/* Created on:     2012-9-10 16:16:26                           */
/*==============================================================*/


if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('car') and o.name = 'FK_CAR_CAR->DIST_DISTRIBU')
alter table car
   drop constraint "FK_CAR_CAR->DIST_DISTRIBU"
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('price') and o.name = 'FK_PRICE_PRICE->FI_DISTRIBU')
alter table price
   drop constraint "FK_PRICE_PRICE->FI_DISTRIBU"
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('price') and o.name = 'FK_PRICE_PRICE->SE_DISTRIBU')
alter table price
   drop constraint "FK_PRICE_PRICE->SE_DISTRIBU"
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('route') and o.name = 'FK_ROUTE_ROUTE->FI_DISTRIBU')
alter table route
   drop constraint "FK_ROUTE_ROUTE->FI_DISTRIBU"
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('route') and o.name = 'FK_ROUTE_ROUTE->SE_DISTRIBU')
alter table route
   drop constraint "FK_ROUTE_ROUTE->SE_DISTRIBU"
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('"user"') and o.name = 'FK_USER_USER->DIS_DISTRIBU')
alter table "user"
   drop constraint "FK_USER_USER->DIS_DISTRIBU"
go

if exists (select 1
            from  sysobjects
           where  id = object_id('car')
            and   type = 'U')
   drop table car
go

if exists (select 1
            from  sysobjects
           where  id = object_id('distribute')
            and   type = 'U')
   drop table distribute
go

if exists (select 1
            from  sysobjects
           where  id = object_id('price')
            and   type = 'U')
   drop table price
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
   car_number           nvarchar(50)         null,
   weight               int                  null,
   volume               int                  null,
   driver               nvarchar(50)         null,
   status               int                  null,
   now_distribute_id    int                  null,
   constraint PK_CAR primary key (id)
)
go

/*==============================================================*/
/* Table: distribute                                            */
/*==============================================================*/
create table distribute (
   id                   int                  identity,
   name                 nvarchar(50)         null,
   address              nvarchar(50)         null,
   linkman              nvarchar(50)         null,
   telephone            nvarchar(50)         null,
   constraint PK_DISTRIBUTE primary key (id)
)
go

/*==============================================================*/
/* Table: price                                                 */
/*==============================================================*/
create table price (
   id                   int                  identity,
   first_distribute_id  int                  null,
   second_distribute_id int                  null,
   firstKg_price        int                  null,
   secondKg_price       int                  null,
   constraint PK_PRICE primary key (id)
)
go

/*==============================================================*/
/* Table: route                                                 */
/*==============================================================*/
create table route (
   id                   int                  identity,
   name                 nvarchar(50)         null,
   first_distribute_id  int                  null,
   second_distribute_id int                  null,
   length               int                  null,
   constraint PK_ROUTE primary key (id)
)
go

/*==============================================================*/
/* Table: "user"                                                */
/*==============================================================*/
create table "user" (
   id                   int                  identity,
   username             nvarchar(50)         null,
   password             nvarchar(50)         null,
   name                 nvarchar(50)         null,
   telephone            nvarchar(50)         null,
   distribute_id        int                  null,
   user_level           int                  null,
   constraint PK_USER primary key (id)
)
go

alter table car
   add constraint "FK_CAR_CAR->DIST_DISTRIBU" foreign key (now_distribute_id)
      references distribute (id)
go

alter table price
   add constraint "FK_PRICE_PRICE->FI_DISTRIBU" foreign key (first_distribute_id)
      references distribute (id)
go

alter table price
   add constraint "FK_PRICE_PRICE->SE_DISTRIBU" foreign key (second_distribute_id)
      references distribute (id)
go

alter table route
   add constraint "FK_ROUTE_ROUTE->FI_DISTRIBU" foreign key (first_distribute_id)
      references distribute (id)
go

alter table route
   add constraint "FK_ROUTE_ROUTE->SE_DISTRIBU" foreign key (second_distribute_id)
      references distribute (id)
go

alter table "user"
   add constraint "FK_USER_USER->DIS_DISTRIBU" foreign key (distribute_id)
      references distribute (id)
go

