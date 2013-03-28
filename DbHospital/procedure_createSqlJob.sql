/*==============================================================*/
/* Database name:  dbhospital                                   */
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2013-01-10 星期四 12:25                     */
/* Description:    创建一个存储过程，用来建立SQL Server的定时任务 */
/*==============================================================*/


----每月执行的作业
--exec p_createjob @jobname='mm',@sql='select * from syscolumns',@freqtype='month'
----每周执行的作业
--exec p_createjob @jobname='ww',@sql='select * from syscolumns',@freqtype='week'
----每日执行的作业
--exec p_createjob @jobname='a',@sql='select * from syscolumns'
----每日执行的作业,每天隔4小时重复的作业
--exec p_createjob @jobname='b',@sql='select * from syscolumns',@fsinterval=4


--删除原有的存储过程，如果存在的话
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[p_createjob]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure [dbo].[p_createjob]
GO

--创建该存储过程
create proc p_createjob
@jobname varchar(100),         --作业名称
@sql varchar(8000),            --要执行的命令
@dbname sysname='',            --默认为当前的数据库名
@freqtype varchar(6)='day',    --时间周期,month 月,week 周,day 日
@fsinterval int=1,             --相对于每日的重复次数
@time int=170000               --开始执行时间,对于重复执行的作业,将从0点到23:59分
as

--设置数据库名称
if isnull(@dbname,'')='' set @dbname=db_name()
--根据名称创建作业
exec msdb..sp_add_job @job_name=@jobname

--创建步骤
exec msdb..sp_add_jobstep @job_name=@jobname,
@step_name = '数据处理',
@subsystem = 'TSQL',
@database_name=@dbname,
@command = @sql,
@retry_attempts = 5, --重试次数
@retry_interval = 5  --重试间隔

--创建调度（计划）
declare @ftype int,@fstype int,@ffactor int
select @ftype=case @freqtype when 'day' then 4
when 'week' then 8
when 'month' then 16 end
,@fstype=case @fsinterval when 1 then 0 else 8 end
if @fsinterval<>1 set @time=0
set @ffactor=case @freqtype when 'day' then 0 else 1 end

--执行创建作业
EXEC msdb..sp_add_jobschedule @job_name=@jobname, 
@name = '时间安排',
@freq_type=@ftype ,                 --4 每天,8 每周,16 每月
@freq_interval=1,                   --重复执行次数
@freq_subday_type=@fstype,          --是否重复执行
@freq_subday_interval=@fsinterval,  --重复周期
@freq_recurrence_factor=@ffactor,
@active_start_time=@time            --下午17:00:00分执行

--添加到目标服务器
EXEC msdb.dbo.sp_add_jobserver 
@job_name = @jobname ,
@server_name = N'(local)'           --服务器默认是本地服务器
go

