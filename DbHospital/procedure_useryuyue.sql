-- =============================================
-- Author:		胡家威
-- Create date: 2013-1-15
-- Description:	用户预约操作，返回操作的结果信息
-- =============================================
USE [dbhospital]
GO

CREATE PROCEDURE p_useryuyue

@count1 int,  --同一患者实名（有效证件号）在同一就诊日、同一科室只能预约 count1 次
@count2 int,  --在同一就诊日的预约总量不可超过 count2 次
@count3 int,  --在一个月内的预约总量不可超过 count3 次
@userid int,  --预约的排班
@aid int,     --预约的用户
@type int,    --预约类型
@validate int, --是否有效
@number varchar(32), --候诊号
@result varchar(128) output  --要输出的信息

AS

declare @date datetime --要预约的时间
declare @rooomid int   --要预约的科室
declare @count1now int --count1的当前值
declare @count2now int --count2的当前值
declare @count3now int --count3的当前值

select @date=t_arrangement.date,@rooomid=t_arrangement.room_id from t_arrangement where id=@aid
if exists(select t_register.id from t_register where arrangement_id=@aid and user_id=@userid and type=0)
	begin
	select @result = '您已经预约了'
	end
else  --只管是预约类型，不管是否有效了
	begin
	select @count1now=count(distinct(t_register.id)) from t_register where t_register.user_id=@userid and type=0		
		and t_register.arrangement_id in(select t_arrangement.id from t_arrangement
		    where @rooomid=t_arrangement.room_id and DATEDIFF(DAY,@date,t_arrangement.date)=0)
		    --科室，时间 
		if(@count1now >= @count1)
			begin
			select 	@result = '您在同一就诊日、同一科室最多只能预约'+convert(varchar,@count1)+'次'
			end
		else 
			begin
			select @count2now=count(distinct(t_register.id)) from t_register,t_arrangement where t_register.user_id=@userid and type=0
				and t_register.arrangement_id in(select t_arrangement.id from t_arrangement
				    where DATEDIFF(DAY,@date,t_arrangement.date)=0) 
				    --时间
				if(@count2now >= @count2)
					begin
					select 	@result = '您在同一就诊日最多只能预约'+convert(varchar,@count2)+'次'
					end
				else 
					begin	
					select @count3now=count(distinct(t_register.id)) from t_register,t_arrangement where t_register.user_id=@userid and type=0
						and t_register.arrangement_id in(select t_arrangement.id from t_arrangement
						where DATEDIFF(YEAR,@date,t_arrangement.date)=0 and DATEDIFF(MONTH,@date,t_arrangement.date)=0) 
						--同年同月
					if(@count3now >= @count3)
						begin
						select 	@result = '您在一个月内最多只能预约'+convert(varchar,@count3)+'次'
						end
					else
						begin
						insert into t_register(user_id,arrangement_id,number,type,validate) values(@userid,@aid,@number,@type,@validate);
						select @result = '预约成功'
						end
					end
			end
	end
GO
