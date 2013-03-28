/*==============================================================*/
/* Database name:  dbhospital                                   */
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2013-01-10 ������ 12:25                     */
/* Description:    ����һ���洢���̣���������SQL Server�Ķ�ʱ���� */
/*==============================================================*/


----ÿ��ִ�е���ҵ
--exec p_createjob @jobname='mm',@sql='select * from syscolumns',@freqtype='month'
----ÿ��ִ�е���ҵ
--exec p_createjob @jobname='ww',@sql='select * from syscolumns',@freqtype='week'
----ÿ��ִ�е���ҵ
--exec p_createjob @jobname='a',@sql='select * from syscolumns'
----ÿ��ִ�е���ҵ,ÿ���4Сʱ�ظ�����ҵ
--exec p_createjob @jobname='b',@sql='select * from syscolumns',@fsinterval=4


--ɾ��ԭ�еĴ洢���̣�������ڵĻ�
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[p_createjob]') and OBJECTPROPERTY(id, N'IsProcedure') = 1)
drop procedure [dbo].[p_createjob]
GO

--�����ô洢����
create proc p_createjob
@jobname varchar(100),         --��ҵ����
@sql varchar(8000),            --Ҫִ�е�����
@dbname sysname='',            --Ĭ��Ϊ��ǰ�����ݿ���
@freqtype varchar(6)='day',    --ʱ������,month ��,week ��,day ��
@fsinterval int=1,             --�����ÿ�յ��ظ�����
@time int=170000               --��ʼִ��ʱ��,�����ظ�ִ�е���ҵ,����0�㵽23:59��
as

--�������ݿ�����
if isnull(@dbname,'')='' set @dbname=db_name()
--�������ƴ�����ҵ
exec msdb..sp_add_job @job_name=@jobname

--��������
exec msdb..sp_add_jobstep @job_name=@jobname,
@step_name = '���ݴ���',
@subsystem = 'TSQL',
@database_name=@dbname,
@command = @sql,
@retry_attempts = 5, --���Դ���
@retry_interval = 5  --���Լ��

--�������ȣ��ƻ���
declare @ftype int,@fstype int,@ffactor int
select @ftype=case @freqtype when 'day' then 4
when 'week' then 8
when 'month' then 16 end
,@fstype=case @fsinterval when 1 then 0 else 8 end
if @fsinterval<>1 set @time=0
set @ffactor=case @freqtype when 'day' then 0 else 1 end

--ִ�д�����ҵ
EXEC msdb..sp_add_jobschedule @job_name=@jobname, 
@name = 'ʱ�䰲��',
@freq_type=@ftype ,                 --4 ÿ��,8 ÿ��,16 ÿ��
@freq_interval=1,                   --�ظ�ִ�д���
@freq_subday_type=@fstype,          --�Ƿ��ظ�ִ��
@freq_subday_interval=@fsinterval,  --�ظ�����
@freq_recurrence_factor=@ffactor,
@active_start_time=@time            --����17:00:00��ִ��

--��ӵ�Ŀ�������
EXEC msdb.dbo.sp_add_jobserver 
@job_name = @jobname ,
@server_name = N'(local)'           --������Ĭ���Ǳ��ط�����
go

