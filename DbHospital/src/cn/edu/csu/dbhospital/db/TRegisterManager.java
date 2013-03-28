package cn.edu.csu.dbhospital.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cn.edu.csu.dbhospital.pojo.TRegister;
import cn.edu.csu.dbhospital.pojo.TTableRegister;
import cn.edu.csu.dbhospital.util.SystemUtil;

/**
 * 科室db manager
 * 
 * @author hjw
 * 
 */
public class TRegisterManager {

	// 预约操作:调用存储过程 ProcExcel
	public String callProcYuyue(int userid, int aid, int type, int validate, String number) throws SQLException {
		System.out.println("call yuyue");
		Connection dbConn = DataBaseTool.getConnection();
		CallableStatement statement = dbConn.prepareCall("{call p_useryuyue(?,?,?,?,?,?,?,?,?)}");
		statement.setInt(1, SystemUtil.COUNT1_VALUE);
		statement.setInt(2, SystemUtil.COUNT2_VALUE);
		statement.setInt(3, SystemUtil.COUNT3_VALUE);
		statement.setInt(4, userid);
		statement.setInt(5, aid);
		statement.setInt(6, type);
		statement.setInt(7, validate);
		statement.setString(8, number);
		statement.registerOutParameter(9, java.sql.Types.VARCHAR);
		statement.execute();
		String result = statement.getString(9);
		DataBaseTool.close(null, statement, dbConn);
		return result;
	}

	// 查询预约
	public ArrayList<TTableRegister> search(int userid, boolean isAll) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		StringBuffer sql = new StringBuffer("select ");
		if (!isAll) {
			sql.append(" top 3 ");
		}
		sql.append(" a.date as date,d.name as doctorName,room.name as roomName,a.expense as expense,r.id as registerId,r.arrangement_id as arrangementId,r.user_id as userId,r.type as type,r.number as number from t_arrangement as a,t_register as r,t_doctor as d,t_room as room "
				+ " where r.user_id="
				+ userid
				+ " and a.id=r.arrangement_id and a.doctor_id=d.id and a.room_id=room.id and r.type="
				+ TRegister.TYPE_YUYUE + " order by date desc");
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql.toString());
		ArrayList<TTableRegister> list = new ArrayList<TTableRegister>();
		TTableRegister item = null;
		while (rs.next()) {
			item = new TTableRegister();
			setOneTableRegister(item, rs);
			list.add(item);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 查询预约
	public ArrayList<TTableRegister> searchYuyueByCardnum(String cardnum) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select a.date as date,d.name as doctorName,room.name as roomName,a.expense as expense,r.id as registerId,r.arrangement_id as arrangementId,r.user_id as userId,r.type as type,r.number as number from t_arrangement as a,t_register as r,t_doctor as d,t_room as room,t_user as u "
				+ "where a.id=r.arrangement_id and a.doctor_id=d.id and a.room_id=room.id and r.user_id=u.id and DATEDIFF(DAY,a.date,GETDATE())=0 and u.cardnum='"
				+ cardnum + "' and r.type=" + TRegister.TYPE_YUYUE;
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TTableRegister> list = new ArrayList<TTableRegister>();
		TTableRegister item = null;
		while (rs.next()) {
			item = new TTableRegister();
			setOneTableRegister(item, rs);
			list.add(item);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 将数据库中的一条记录构造成一个对应的对象
	private void setOneTableRegister(TTableRegister register, ResultSet rs) throws Exception {
		register.arrangementId = rs.getInt("arrangementId");
		register.date = rs.getDate("date");
		register.doctorName = rs.getString("doctorName");
		register.expense = rs.getFloat("expense");
		register.number = rs.getString("number");
		register.roomName = rs.getString("roomName");
		register.type = rs.getInt("type");
		register.userId = rs.getInt("userId");
		register.registerId = rs.getInt("registerId");
	}

	// 添加
	public int add(TRegister register) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "insert into t_register(user_id,arrangement_id,number,type,validate) values("
				+ register.getUserId() + "," + register.getArrangementId() + ",'" + register.getNumber() + "',"
				+ register.getType() + "," + register.getValidate() + ")";
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 删除
	public int delete(int rid) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "delete from t_register where id=" + rid;
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 更新
	public int update(TRegister register) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "update t_register set user_id=" + register.getUserId() + " , arrangement_id="
				+ register.getArrangementId() + " , type=" + register.getType() + " , validate="
				+ register.getValidate() + " , number='" + register.getNumber() + "' where id=" + register.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 查询所有
	public ArrayList<TRegister> searchAll() throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_register";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TRegister> list = new ArrayList<TRegister>();
		TRegister register = null;
		while (rs.next()) {
			register = new TRegister();
			setOne(register, rs);
			list.add(register);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 查询 top number
	public ArrayList<TTableRegister> searchGuahao() throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select top 20 a.date as date,d.name as doctorName,room.name as roomName,a.expense as expense,r.id as registerId,r.arrangement_id as arrangementId,r.user_id as userId,r.type as type,r.number as number from t_arrangement as a,t_register as r,t_doctor as d,t_room as room "
				+ " where a.id=r.arrangement_id and a.doctor_id=d.id and a.room_id=room.id and r.type="
				+ TRegister.TYPE_GUAHAO + " order by date desc";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TTableRegister> list = new ArrayList<TTableRegister>();
		TTableRegister item = null;
		while (rs.next()) {
			item = new TTableRegister();
			setOneTableRegister(item, rs);
			list.add(item);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 根据id查询
	public TRegister searchByID(int id) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_register where id=" + id;
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		TRegister register = null;
		while (rs.next()) {
			register = new TRegister();
			setOne(register, rs);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return register;
	}

	// 将数据库中的一条记录构造成一个对应的对象
	private void setOne(TRegister register, ResultSet rs) throws Exception {
		register.setId(rs.getInt("id"));
		register.setArrangementId(rs.getInt("arrangement_id"));
		register.setUserId(rs.getInt("user_id"));
		register.setType(rs.getInt("type"));
		register.setValidate(rs.getInt("validate"));
		register.setNumber(rs.getString("number"));
	}

}
