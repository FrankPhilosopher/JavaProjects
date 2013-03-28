-- =============================================
-- Author:		������
-- Create date: 2013-1-15
-- Description:	�û�ԤԼ���������ز����Ľ����Ϣ
-- =============================================
USE [dbhospital]
GO

CREATE PROCEDURE p_useryuyue

@count1 int,  --ͬһ����ʵ������Ч֤���ţ���ͬһ�����ա�ͬһ����ֻ��ԤԼ count1 ��
@count2 int,  --��ͬһ�����յ�ԤԼ�������ɳ��� count2 ��
@count3 int,  --��һ�����ڵ�ԤԼ�������ɳ��� count3 ��
@userid int,  --ԤԼ���Ű�
@aid int,     --ԤԼ���û�
@type int,    --ԤԼ����
@validate int, --�Ƿ���Ч
@number varchar(32), --�����
@result varchar(128) output  --Ҫ�������Ϣ

AS

declare @date datetime --ҪԤԼ��ʱ��
declare @rooomid int   --ҪԤԼ�Ŀ���
declare @count1now int --count1�ĵ�ǰֵ
declare @count2now int --count2�ĵ�ǰֵ
declare @count3now int --count3�ĵ�ǰֵ

select @date=t_arrangement.date,@rooomid=t_arrangement.room_id from t_arrangement where id=@aid
if exists(select t_register.id from t_register where arrangement_id=@aid and user_id=@userid and type=0)
	begin
	select @result = '���Ѿ�ԤԼ��'
	end
else  --ֻ����ԤԼ���ͣ������Ƿ���Ч��
	begin
	select @count1now=count(distinct(t_register.id)) from t_register where t_register.user_id=@userid and type=0		
		and t_register.arrangement_id in(select t_arrangement.id from t_arrangement
		    where @rooomid=t_arrangement.room_id and DATEDIFF(DAY,@date,t_arrangement.date)=0)
		    --���ң�ʱ�� 
		if(@count1now >= @count1)
			begin
			select 	@result = '����ͬһ�����ա�ͬһ�������ֻ��ԤԼ'+convert(varchar,@count1)+'��'
			end
		else 
			begin
			select @count2now=count(distinct(t_register.id)) from t_register,t_arrangement where t_register.user_id=@userid and type=0
				and t_register.arrangement_id in(select t_arrangement.id from t_arrangement
				    where DATEDIFF(DAY,@date,t_arrangement.date)=0) 
				    --ʱ��
				if(@count2now >= @count2)
					begin
					select 	@result = '����ͬһ���������ֻ��ԤԼ'+convert(varchar,@count2)+'��'
					end
				else 
					begin	
					select @count3now=count(distinct(t_register.id)) from t_register,t_arrangement where t_register.user_id=@userid and type=0
						and t_register.arrangement_id in(select t_arrangement.id from t_arrangement
						where DATEDIFF(YEAR,@date,t_arrangement.date)=0 and DATEDIFF(MONTH,@date,t_arrangement.date)=0) 
						--ͬ��ͬ��
					if(@count3now >= @count3)
						begin
						select 	@result = '����һ���������ֻ��ԤԼ'+convert(varchar,@count3)+'��'
						end
					else
						begin
						insert into t_register(user_id,arrangement_id,number,type,validate) values(@userid,@aid,@number,@type,@validate);
						select @result = 'ԤԼ�ɹ�'
						end
					end
			end
	end
GO
