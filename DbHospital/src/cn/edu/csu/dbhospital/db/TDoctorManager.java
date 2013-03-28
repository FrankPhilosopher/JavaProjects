package cn.edu.csu.dbhospital.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import cn.edu.csu.dbhospital.pojo.TDoctor;

/**
 * 医生db manager
 * 
 * @author hjw
 * 
 */
public class TDoctorManager {

	// 添加
	// insert into t_doctor(name,gander,title,phone,info,room_id) values ('小武',1,'主治医生','13752568888','本院最优秀的主治医生',1)
	public int add(TDoctor doctor) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "insert into t_doctor(name,gander,title,phone,info,room_id) values ('" + doctor.getName() + "',"
				+ doctor.getGander() + ",'" + doctor.getTitle() + "','" + doctor.getPhone() + "','" + doctor.getInfo()
				+ "'," + doctor.getRoomId() + ")";
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 删除
	public int delete(TDoctor doctor) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "delete from t_doctor where id=" + doctor.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 更新
	// update t_doctor set name='小武',gander=1,title='院长',phone='13752568888',info='本院院长',room_id=1 where id=1
	public int update(TDoctor doctor) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "update t_doctor set name='" + doctor.getName() + "',gander=" + doctor.getGander() + ",title='"
				+ doctor.getTitle() + "',phone='" + doctor.getPhone() + "',info='" + doctor.getInfo() + "',room_id="
				+ doctor.getRoomId() + " where id=" + doctor.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 查询所有
	public ArrayList<TDoctor> searchAll() throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_doctor";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TDoctor> list = new ArrayList<TDoctor>();
		TDoctor doctor = null;
		while (rs.next()) {
			doctor = new TDoctor();
			setOne(doctor, rs);
			list.add(doctor);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 根据名字查询
	public ArrayList<TDoctor> searchByName(String name) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_doctor where name like '%" + name + "%' ";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TDoctor> list = new ArrayList<TDoctor>();
		TDoctor doctor = null;
		while (rs.next()) {
			doctor = new TDoctor();
			setOne(doctor, rs);
			list.add(doctor);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 根据id查询
	public TDoctor searchById(int id) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_doctor where id=" + id;
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		TDoctor doctor = null;
		while (rs.next()) {
			doctor = new TDoctor();
			setOne(doctor, rs);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return doctor;
	}

	// 根据科室查询
	public ArrayList<TDoctor> searchByRoom(int roomid) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_doctor where room_id=" + roomid;
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TDoctor> list = new ArrayList<TDoctor>();
		TDoctor doctor = null;
		while (rs.next()) {
			doctor = new TDoctor();
			setOne(doctor, rs);
			list.add(doctor);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 将数据库中的一条记录构造成一个对应的对象
	private void setOne(TDoctor doctor, ResultSet rs) throws Exception {
		doctor.setId(rs.getInt("id"));
		doctor.setName(rs.getString("name"));
		doctor.setTitle(rs.getString("title"));
		doctor.setInfo(rs.getString("info"));
		doctor.setPhone(rs.getString("phone"));
		doctor.setRegtime(rs.getDate("regtime"));
		doctor.setRoomId(rs.getInt("room_id"));
		doctor.setGander(rs.getInt("gander"));
	}

}
