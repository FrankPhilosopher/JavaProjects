/*==============================================================*/
/* Database name:  dbhospital                                   */
/* DBMS name:      Microsoft SQL Server 2008                    */
/* Created on:     2013-01-10 ������ 12:25                     */
/* Description:    ����һ����������ɾ������֮ǰ��ɾ�����ҵ�����ҽ������ */
/*==============================================================*/

CREATE TRIGGER trigger_deleteRoom
ON  t_room
INSTEAD OF DELETE
AS 
--�õ�Ҫɾ���Ŀ��ҵ�id
DECLARE @roomid int
SELECT @roomid = Deleted.id FROM Deleted
BEGIN
	--��ɾ�����ҵ�ҽ������
	DELETE FROM t_doctor WHERE room_id=@roomid
	--Ȼ��ɾ������
	DELETE FROM t_room WHERE id=@roomid	
END
GO
