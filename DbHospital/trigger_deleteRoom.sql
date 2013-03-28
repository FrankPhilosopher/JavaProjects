/*==============================================================*/
/* Database name:  dbhospital                                   */
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2013-01-10 星期四 12:25                     */
/* Description:    创建一个触发器，删除科室之前先删除科室的所有医生数据 */
/*==============================================================*/

CREATE TRIGGER trigger_deleteRoom
ON  t_room
INSTEAD OF DELETE
AS 
--得到要删除的科室的id
DECLARE @roomid int
SELECT @roomid = Deleted.id FROM Deleted
BEGIN
	--先删除科室的医生数据
	DELETE FROM t_doctor WHERE room_id=@roomid
	--然后删除科室
	DELETE FROM t_room WHERE id=@roomid	
END
GO
