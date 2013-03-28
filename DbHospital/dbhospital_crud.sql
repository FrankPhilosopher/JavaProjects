--exec p_createjob @jobname='job',@sql='select * from t_worker',@time=131600


--insert into t_doctor(name,gander,title,phone,info,room_id) values ('小武',1,'主治医生','13752568888','本院最优秀的主治医生',1)

--update t_doctor set name='小武',gander=1,title='院长',phone='13752568888',info='本院院长',room_id=1 where id=1


--select * from t_arrangement where room_id=1 and date between 2013/01/13 and 2013/02/12

--insert into t_arrangement(doctor_id,room_id,expense,limit,date,num) values (1,1,200.0,3,'2013-01-14',0)

--select * from t_arrangement where room_id=1 and date between '2013/01/13' and '2013/02/12'

--update t_arrangement set doctor_id=,room_id=,expense=,limit=,date=,num=

--convert(datetime,'2011/11/12',120)
--insert into t_arrangement(doctor_id,room_id,expense,limit,date,num) values (1,1,200.0,3,convert(datetime,'2013-01-14',120),0)

--update t_worker set name='小武',gander=1,realname='院长',password='13752568888',phone='13752568888',canModify=0,canStatics=0 where id=1

--insert into t_worker(name,realname,password,phone,gander,canModify,canStatics) values ('','','','',,,)

--insert into t_user(name,realname,password,phone,gander,card) values ('xiaohu','小胡','123','15084953317',1,362203199101120410)

--delete from t_card where id=3

--select * from t_arrangement where room_id=1 and date between '2013-01-14' and '2013-01-20'

--select count(id) from t_register where arrangement_id = 14

--insert into t_register(user_id,arrangement_id,number,type,validate) values()

--delete  from t_register

--declare @count1now int

--select @count1now=count(distinct(t_register.id)) from t_register where t_register.user_id=9 and type=0		
--		and t_register.arrangement_id in(select t_arrangement.id from t_arrangement
--		    where 1=t_arrangement.room_id and DATEDIFF(DAY,'2013-01-16',t_arrangement.date)=0) 
--print @count1now

--select r.user_id,r.type,a.date,a.room_id,a.limit from t_register as r,t_arrangement as a where a.id = r.arrangement_id
--and DATEDIFF(DAY,'2013-01-17',a.date)=0

--select t_register.id from t_register where arrangement_id=14 and user_id=9

--select id,arrangement_id,user_id from t_register


--select a.date as date,d.name as doctorName,room.name as roomName,a.expense as expense,r.arrangement_id as arrangementId,r.user_id as userId,r.type as type,r.number as number from t_arrangement as a,t_register as r,t_doctor as d,t_room as room 
--where r.user_id=9 and a.id=r.arrangement_id and a.doctor_id=d.id and a.room_id=room.id

--select top 20 r.number,u.realname,u.gander,u.phone,u.cardnum,room.name from t_register as r,t_arrangement as a,t_user as u,t_room as room
--where r.arrangement_id=a.id and u.id=a.id and a.room_id=room.id 
--and a.num=''

--select a.date as date,d.name as doctorName,room.name as roomName,a.expense as expense,r.id as registerId,r.arrangement_id as arrangementId,r.user_id as userId,r.type as type,r.number as number from t_arrangement as a,t_register as r,t_doctor as d,t_room as room,t_user as u 
--where a.id=r.arrangement_id and a.doctor_id=d.id and a.room_id=room.id and r.user_id=u.id and DATEDIFF(DAY,a.date,GETDATE())=0 and u.cardnum='ZLbe3ffdd' and r.type=0

--select a.date as date,d.name as doctorName,room.name as roomName,a.expense as expense,r.id as registerId,r.arrangement_id as arrangementId,r.user_id as userId,r.type as type,r.number as number from t_arrangement as a,t_register as r,t_doctor as d,t_room as room,t_user as u where a.id=r.arrangement_id and a.doctor_id=d.id and a.room_id=room.id and r.user_id=u.id and DATEDIFF(DAY,a.date,GETDATE())=0 and u.cardnum='ZLbe3ffdd' and r.type=0

--select a.num as num,a.limit as limit,a.expense as expense,d.name as doctorName,a.id as aid from t_arrangement as a,t_room as r,t_doctor as d 
--where a.doctor_id=d.id and a.room_id=r.id and DATEDIFF(DAY,a.date,GETDATE())=0 and r.id=1

select a.num as num,a.limit as limit,a.expense as expense,d.name as doctorName,a.id as aid from t_arrangement as a,t_room as r,t_doctor as d where a.doctor_id=d.id and a.room_id=r.id and DATEDIFF(DAY,a.date,GETDATE())=0 and r.id=1



