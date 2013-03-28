package cn.edu.csu.dbhospital.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import cn.edu.csu.dbhospital.pojo.TRoom;

/**
 * 科室db manager
 * 
 * @author hjw
 * 
 */
public class TRoomManager {

	// 添加
	public int add(TRoom room) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "insert into t_room(name,info) values ('" + room.getName() + "','" + room.getInfo() + "')";
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 删除
	public int delete(TRoom room) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "delete from t_room where id=" + room.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 更新
	public int update(TRoom room) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "update t_room set name='" + room.getName() + "' , info='" + room.getInfo() + "' where id="
				+ room.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 查询所有
	public ArrayList<TRoom> searchAll() throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_room";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TRoom> list = new ArrayList<TRoom>();
		TRoom room = null;
		while (rs.next()) {
			room = new TRoom();
			setOne(room, rs);
			list.add(room);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 根据id查询
	public TRoom searchByID(int id) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_room where id=" + id;
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		TRoom room = null;
		while (rs.next()) {
			room = new TRoom();
			setOne(room, rs);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return room;
	}

	// 根据名字查询
	public ArrayList<TRoom> searchByName(String name) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_room where name like '%" + name + "%' ";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TRoom> list = new ArrayList<TRoom>();
		TRoom room = null;
		while (rs.next()) {
			room = new TRoom();
			setOne(room, rs);
			list.add(room);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 将数据库中的一条记录构造成一个对应的对象
	private void setOne(TRoom room, ResultSet rs) throws Exception {
		room.setId(rs.getInt("id"));
		room.setName(rs.getString("name"));
		room.setInfo(rs.getString("info"));
	}

}
