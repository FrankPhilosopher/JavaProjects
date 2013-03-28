/*==============================================================*/
/* DBMS name:      Sybase SQL Anywhere 10                       */
/* Created on:     2012-09-10 14:15:59                          */
/*==============================================================*/

/*
if exists(
   select 1 from sys.systable 
   where table_name='car'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table car
end if;

if exists(
   select 1 from sys.systable 
   where table_name='orders'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table orders
end if;

if exists(
   select 1 from sys.systable 
   where table_name='passingSheet'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table passingSheet
end if;

if exists(
   select 1 from sys.systable 
   where table_name='placePrice'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table placePrice
end if;

if exists(
   select 1 from sys.systable 
   where table_name='user'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table "user"
end if;
*/
/*==============================================================*/
/* Table: car                                                   */
/*==============================================================*/
create table car 
(
   id                   int,
   car_id               varchar(50),
   driver               varchar(50),
   car_weight           float,
   car_volume           int,
   current_distribution_id int,
   distribution_name    varchar(50),
   status               int
);

/*==============================================================*/
/* Table: orders                                                */
/*==============================================================*/
create table orders 
(
   id                   int                            not null,
   receive              varchar(50),
   status               int,
   receive_address      varchar(50),
   receive_name         varchar(50),
   send_address         varchar(50),
   send_phone           varchar(50),
   send_postCode        varchar(50),
   send_weight          float,
   send_volume          int,
   send_num             int,
   send_type            int,
   gather_date          datetime,
   gather_name          varchar(50),
   send_date            datetime,
   note                 varchar(100),
   price                float,
   currentLocation      varchar(50),
   route_id             int,
   next_id              int,
   current_id           int,
   receive_phone        varchar(50),
   receive_state        int,
   place_name           varchar(50),
   car_id               int,
   constraint PK_ORDERS primary key clustered (id)
);

/*==============================================================*/
/* Table: passingSheet                                          */
/*==============================================================*/
create table passingSheet 
(
   passing_id           int                            not null,
   car_id               int,
   distribution_name    varchar(50),
   change_name          varchar(50),
   passing_date         datetime,
   order_id             int,
   constraint PK_PASSINGSHEET primary key clustered (passing_id)
);

/*==============================================================*/
/* Table: placePrice                                            */
/*==============================================================*/
create table placePrice 
(
   id                   int                            not null,
   distribution_id      int,
   place_name           varchar(50),
   place_price          float,
   place_time           varchar(50),
   note                 varchar(100),
   constraint PK_PLACEPRICE primary key clustered (id)
);

/*==============================================================*/
/* Table: "user"                                                */
/*==============================================================*/
create table "user" 
(
   id                   int                            not null,
   userName             varchar(50),
   passWord             varchar(50),
   realName             varchar(20),
   distribution_id      int,
   distribution_name    varchar(50),
   company_id           int,
   company_name         varchar(50),
   phone                varchar(50),
   permission           int,
   constraint PK_USER primary key clustered (id)
);

