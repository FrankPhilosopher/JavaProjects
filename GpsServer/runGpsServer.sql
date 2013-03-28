/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2005                    */
/* Created on:     2012/5/18 21:56:00                           */
/*==============================================================*/


if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_ACCOUNT') and o.name = 'FK_T_ACCOUN_REFERENCE_T_USER')
alter table T_ACCOUNT
   drop constraint FK_T_ACCOUN_REFERENCE_T_USER
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_FENCE') and o.name = 'FK_T_FENCE_REFERENCE_T_TERMIN')
alter table T_FENCE
   drop constraint FK_T_FENCE_REFERENCE_T_TERMIN
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_LOG') and o.name = 'FK_T_LOG_REFERENCE_T_USER')
alter table T_LOG
   drop constraint FK_T_LOG_REFERENCE_T_USER
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_ORGAINZATION') and o.name = 'FK_T_ORGAIN_REFERENCE_T_AREA')
alter table T_ORGAINZATION
   drop constraint FK_T_ORGAIN_REFERENCE_T_AREA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_ORGAINZATION') and o.name = 'FK_T_ORGAIN_REFERENCE_T_ORGAIN')
alter table T_ORGAINZATION
   drop constraint FK_T_ORGAIN_REFERENCE_T_ORGAIN
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_REQUEST') and o.name = 'FK_T_REQUES_REFERENCE_T_TERMIN')
alter table T_REQUEST
   drop constraint FK_T_REQUES_REFERENCE_T_TERMIN
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_RESPONSE') and o.name = 'FK_T_RESPON_REFERENCE_T_TERMIN')
alter table T_RESPONSE
   drop constraint FK_T_RESPON_REFERENCE_T_TERMIN
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_TERMINAL') and o.name = 'FK_T_TERMIN_REFERENCE_T_CAR_IN')
alter table T_TERMINAL
   drop constraint FK_T_TERMIN_REFERENCE_T_CAR_IN
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_TERMINAL') and o.name = 'FK_T_TERMIN_REFERENCE_T_ORGAIN')
alter table T_TERMINAL
   drop constraint FK_T_TERMIN_REFERENCE_T_ORGAIN
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('T_USER') and o.name = 'FK_T_USER_REFERENCE_T_ORGAIN')
alter table T_USER
   drop constraint FK_T_USER_REFERENCE_T_ORGAIN
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('T_AREA')
            and   name  = 'Index_1'
            and   indid > 0
            and   indid < 255)
   drop index T_AREA.Index_1
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_AREA')
            and   type = 'U')
   drop table T_AREA
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('T_ACCOUNT')
            and   name  = 'Index_1'
            and   indid > 0
            and   indid < 255)
   drop index T_ACCOUNT.Index_1
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_ACCOUNT')
            and   type = 'U')
   drop table T_ACCOUNT
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('T_CAR_INFO')
            and   name  = 'Index_1'
            and   indid > 0
            and   indid < 255)
   drop index T_CAR_INFO.Index_1
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_CAR_INFO')
            and   type = 'U')
   drop table T_CAR_INFO
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('T_FENCE')
            and   name  = 'Index_1'
            and   indid > 0
            and   indid < 255)
   drop index T_FENCE.Index_1
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_FENCE')
            and   type = 'U')
   drop table T_FENCE
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('T_LOG')
            and   name  = 'Index_1'
            and   indid > 0
            and   indid < 255)
   drop index T_LOG.Index_1
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_LOG')
            and   type = 'U')
   drop table T_LOG
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('T_ORGAINZATION')
            and   name  = 'Index_1'
            and   indid > 0
            and   indid < 255)
   drop index T_ORGAINZATION.Index_1
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_ORGAINZATION')
            and   type = 'U')
   drop table T_ORGAINZATION
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('T_POSITION')
            and   name  = 'Index_1'
            and   indid > 0
            and   indid < 255)
   drop index T_POSITION.Index_1
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_POSITION')
            and   type = 'U')
   drop table T_POSITION
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('T_REQUEST')
            and   name  = 'key1'
            and   indid > 0
            and   indid < 255)
   drop index T_REQUEST.key1
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_REQUEST')
            and   type = 'U')
   drop table T_REQUEST
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('T_RESPONSE')
            and   name  = 'Index_1'
            and   indid > 0
            and   indid < 255)
   drop index T_RESPONSE.Index_1
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_RESPONSE')
            and   type = 'U')
   drop table T_RESPONSE
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('T_TEMP_LATLNG')
            and   name  = 'Index_1'
            and   indid > 0
            and   indid < 255)
   drop index T_TEMP_LATLNG.Index_1
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_TEMP_LATLNG')
            and   type = 'U')
   drop table T_TEMP_LATLNG
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('T_TEMP_POSITION')
            and   name  = 'Index_1'
            and   indid > 0
            and   indid < 255)
   drop index T_TEMP_POSITION.Index_1
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_TEMP_POSITION')
            and   type = 'U')
   drop table T_TEMP_POSITION
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('T_TERMINAL')
            and   name  = 'Index_2'
            and   indid > 0
            and   indid < 255)
   drop index T_TERMINAL.Index_2
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('T_TERMINAL')
            and   name  = 'Index_1'
            and   indid > 0
            and   indid < 255)
   drop index T_TERMINAL.Index_1
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_TERMINAL')
            and   type = 'U')
   drop table T_TERMINAL
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('T_USER')
            and   name  = 'Index_1'
            and   indid > 0
            and   indid < 255)
   drop index T_USER.Index_1
go

if exists (select 1
            from  sysobjects
           where  id = object_id('T_USER')
            and   type = 'U')
   drop table T_USER
go

/*==============================================================*/
/* Table: T_AREA                                               */
/*==============================================================*/
create table T_AREA (
   AREA_ID              int                  identity,
   NAME                 nvarchar(30)         null,
   constraint PK_T_AREA primary key (AREA_ID)
)
go

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on T_AREA (
AREA_ID ASC
)
go

/*==============================================================*/
/* Table: T_ACCOUNT                                             */
/*==============================================================*/
create table T_ACCOUNT (
   ID                   int                  identity,
   PAIDER               nvarchar(20)         null,
   EXPENSE              int                  null,
   PAIDDATE             datetime             null,
   REMARK               nvarchar(50)         null,
   USER_ID              int                  null,
   constraint PK_T_ACCOUNT primary key (ID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '缴费账单信息记录',
   'user', @CurrentUser, 'table', 'T_ACCOUNT'
go

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on T_ACCOUNT (
ID ASC,
PAIDER ASC
)
go

/*==============================================================*/
/* Table: T_CAR_INFO                                            */
/*==============================================================*/
create table T_CAR_INFO (
   CAR_TYPE_ID          int                  identity,
   TYPE_NAME            nvarchar(10)         null,
   OUTLINE              nvarchar(20)         null,
   ONLINE_ON            nvarchar(20)         null,
   ONLINE_STOP          nvarchar(20)         null,
   ONLINE_WORK          nvarchar(20)         null,
   R1                   nvarchar(20)         null,
   REMARK               nvarchar(20)         null,
   constraint PK_T_CAR_INFO primary key (CAR_TYPE_ID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '车型信息表',
   'user', @CurrentUser, 'table', 'T_CAR_INFO'
go

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on T_CAR_INFO (
CAR_TYPE_ID ASC
)
go

/*==============================================================*/
/* Table: T_FENCE                                               */
/*==============================================================*/
create table T_FENCE (
   ID                   int                  identity,
   SIM_ID               int                  not null,
   ONOFF                int                  null,
   LONGITUDE            float                null,
   LATITUDE             float                null,
   RADIOUS              float                null,
   constraint PK_T_FENCE primary key (ID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '终端电子栅栏信息',
   'user', @CurrentUser, 'table', 'T_FENCE'
go

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create unique index Index_1 on T_FENCE (
SIM_ID ASC
)
go

/*==============================================================*/
/* Table: T_LOG                                                 */
/*==============================================================*/
create table T_LOG (
   LOG_ID               int                  identity,
   USER_ID              int                  null,
   LOG_EVENT            nvarchar(100)        null,
   LOG_TIME             datetime             null,
   constraint PK_T_LOG primary key (LOG_ID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '日志信息表',
   'user', @CurrentUser, 'table', 'T_LOG'
go

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on T_LOG (
LOG_ID ASC
)
go

/*==============================================================*/
/* Table: T_ORGAINZATION                                        */
/*==============================================================*/
create table T_ORGAINZATION (
   ORG_ID               int                  identity,
   UPORG_ID             int                  null,
   ORG_LEVEL            int                  null,
   NAME                 nvarchar(50)         null,
   ADDRESS              nvarchar(50)         null,
   TELEPHONE            nvarchar(20)         null,
   LINKMAN              nvarchar(30)         null,
   CELLPHONE            nvarchar(20)         null,
   REGISTERTIME         datetime             null,
   AREA_ID              int                  null,
   WARNPHONE            nvarchar(20)         null,
   FEESTANDARD          int                  null,
   BALANCE              int                  null,
   SHORT_NAME           nvarchar(50)         null,
   R1                   nvarchar(50)         null,
   R2                   nvarchar(50)         null,
   REMARK               nvarchar(250)        null,
   constraint PK_T_ORGAINZATION primary key (ORG_ID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '组织机构信息',
   'user', @CurrentUser, 'table', 'T_ORGAINZATION'
go

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on T_ORGAINZATION (
ORG_ID ASC
)
go

/*==============================================================*/
/* Table: T_POSITION                                            */
/*==============================================================*/
create table T_POSITION (
   ID                   bigint               identity,
   SIM                  int                  null,
   LOCATION_MODEL       nvarchar(30)         null,
   STATION_ID           nvarchar(15)         null,
   PLOT_ID              nvarchar(15)         null,
   LATI_DIRECTION       nvarchar(10)         null,
   LATITUDE             float                null,
   LONG_DIRECTION       nvarchar(10)         null,
   LONGITUDE            float                null,
   DIRECTION            nvarchar(30)         null,
   SPEED                float                null,
   P_TIME               datetime             null,
   LATOFFSET            float                null,
   LNGOFFSET            float                null,
   STATUS               nvarchar(10)         null,
   WORKTIME             int                  null,
   ELEPRESS             nvarchar(30)         null,
   SIGNAL               int                  null,
   constraint PK_T_POSITION primary key (ID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '当前实时定位信息',
   'user', @CurrentUser, 'table', 'T_POSITION'
go

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on T_POSITION (
ID ASC
)
go

/*==============================================================*/
/* Table: T_REQUEST                                             */
/*==============================================================*/
create table T_REQUEST (
   ID                   int                  identity,
   TIME                 datetime             null,
   COMMAND              nvarchar(40)         null,
   SIM_ID               int                  null,
   constraint PK_T_REQUEST primary key (ID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '与服务器交互 命令请求表',
   'user', @CurrentUser, 'table', 'T_REQUEST'
go

/*==============================================================*/
/* Index: key1                                                  */
/*==============================================================*/
create index key1 on T_REQUEST (
ID ASC
)
go

/*==============================================================*/
/* Table: T_RESPONSE                                            */
/*==============================================================*/
create table T_RESPONSE (
   ID                   int                  identity,
   TME                  datetime             null,
   RESPONSE             nvarchar(40)         null,
   SIM_ID               int                  null,
   constraint PK_T_RESPONSE primary key (ID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '与服务器通讯 反馈信息表',
   'user', @CurrentUser, 'table', 'T_RESPONSE'
go

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on T_RESPONSE (
ID ASC
)
go

/*==============================================================*/
/* Table: T_TEMP_LATLNG                                         */
/*==============================================================*/
create table T_TEMP_LATLNG (
   ID                   int                  identity,
   LAT                  int                  null,
   LNG                  int                  null,
   OFFSET_X             int                  null,
   OFFSET_Y             int                  null,
   OFFSET_LNG           nvarchar(15)         null,
   OFFSET_LAT           nvarchar(15)         null,
   constraint PK_T_TEMP_LATLNG primary key (ID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '谷歌地图纠偏数据表',
   'user', @CurrentUser, 'table', 'T_TEMP_LATLNG'
go

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on T_TEMP_LATLNG (
ID ASC
)
go

/*==============================================================*/
/* Table: T_TEMP_POSITION                                       */
/*==============================================================*/
create table T_TEMP_POSITION (
   SIM                  nvarchar(30)         not null,
   LOCATION_MODEL       nvarchar(30)         null,
   STATION_ID           nvarchar(15)         null,
   PLOT_ID              nvarchar(15)         null,
   LATI_DIRECTION       nvarchar(10)         null,
   LATITUDE             float                null,
   LONG_DIRECTION       nvarchar(10)         null,
   LONGITUDE            float                null,
   DIRECTION            nvarchar(30)         null,
   SPEED                float                null,
   P_TIME               datetime             null,
   LATOFFSET            float                null,
   LNGOFFSET            float                null,
   STATUS               nvarchar(10)         null,
   WORKTIME             int                  null,
   ELEPRESS             nvarchar(30)         null,
   SIGNAL               int                  null,
   constraint PK_T_TEMP_POSITION primary key (SIM)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '当前实时定位信息',
   'user', @CurrentUser, 'table', 'T_TEMP_POSITION'
go

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on T_TEMP_POSITION (
SIM ASC
)
go

/*==============================================================*/
/* Table: T_TERMINAL                                            */
/*==============================================================*/
create table T_TERMINAL (
   ID                   int                  identity,
   ORG_ID               int                  null,
   SIM                  nvarchar(30)         null,
   PHONE                nvarchar(20)         null,
   CAR_TYPE_ID          int                  null,
   MODEL                nvarchar(30)         null,
   CARNUMBER            nvarchar(40)         null,
   GAS                  nvarchar(30)         null,
   REGISTERTIME         datetime             null,
   START_TIME           nvarchar(30)         null,
   END_TIME             nvarchar(30)         null,
   PRIVILEGE1           nvarchar(30)         null,
   PRIVILEGE2           nvarchar(30)         null,
   PRIVILEGE3           nvarchar(30)         null,
   P_PERIOD             int                  null,
   BASEKILO             int                  null,
   MAINTENANCE          int                  null,
   USERNAME             nvarchar(30)         null,
   CELLPHONE            nvarchar(30)         null,
   AREA_ID              int                  null,
   PRINCIPAL            nvarchar(30)         null,
   REMARK               nvarchar(100)        null,
   constraint PK_T_TERMINAL primary key (ID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '终端信息',
   'user', @CurrentUser, 'table', 'T_TERMINAL'
go

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on T_TERMINAL (
ID ASC
)
go

/*==============================================================*/
/* Index: Index_2                                               */
/*==============================================================*/
create index Index_2 on T_TERMINAL (
SIM ASC
)
go

/*==============================================================*/
/* Table: T_USER                                                */
/*==============================================================*/
create table T_USER (
   ID                   int                  identity,
   ORG_ID               int                  null,
   USERID               nvarchar(20)         null,
   PWD                  nvarchar(30)         null,
   NAME                 nvarchar(30)         null,
   REGISTERTIME         datetime             null,
   CELLPHONE            nvarchar(30)         null,
   OILELE               int                  null,
   MODIFY               int                  null,
   EXPORT               int                  null,
   constraint PK_T_USER primary key (ID)
)
go

declare @CurrentUser sysname
select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description', 
   '登陆账户信息表',
   'user', @CurrentUser, 'table', 'T_USER'
go

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create index Index_1 on T_USER (
ID ASC
)
go

alter table T_ACCOUNT
   add constraint FK_T_ACCOUN_REFERENCE_T_USER foreign key (USER_ID)
      references T_USER (ID)
go

alter table T_FENCE
   add constraint FK_T_FENCE_REFERENCE_T_TERMIN foreign key (SIM_ID)
      references T_TERMINAL (ID)
go

alter table T_LOG
   add constraint FK_T_LOG_REFERENCE_T_USER foreign key (USER_ID)
      references T_USER (ID)
go

alter table T_ORGAINZATION
   add constraint FK_T_ORGAIN_REFERENCE_T_AREA foreign key (AREA_ID)
      references T_AREA (AREA_ID)
go

alter table T_ORGAINZATION
   add constraint FK_T_ORGAIN_REFERENCE_T_ORGAIN foreign key (UPORG_ID)
      references T_ORGAINZATION (ORG_ID)
go

alter table T_REQUEST
   add constraint FK_T_REQUES_REFERENCE_T_TERMIN foreign key (SIM_ID)
      references T_TERMINAL (ID)
go

alter table T_RESPONSE
   add constraint FK_T_RESPON_REFERENCE_T_TERMIN foreign key (SIM_ID)
      references T_TERMINAL (ID)
go

alter table T_TERMINAL
   add constraint FK_T_TERMIN_REFERENCE_T_CAR_IN foreign key (CAR_TYPE_ID)
      references T_CAR_INFO (CAR_TYPE_ID)
go

alter table T_TERMINAL
   add constraint FK_T_TERMIN_REFERENCE_T_ORGAIN foreign key (ORG_ID)
      references T_ORGAINZATION (ORG_ID)
go

alter table T_USER
   add constraint FK_T_USER_REFERENCE_T_ORGAIN foreign key (ORG_ID)
      references T_ORGAINZATION (ORG_ID)
go

