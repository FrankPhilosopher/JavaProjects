package cn.edu.csu.dbhospital.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import cn.edu.csu.dbhospital.pojo.TArrangement;
import cn.edu.csu.dbhospital.pojo.TTableGuahao;
import cn.edu.csu.dbhospital.util.SystemUtil;

/**
 * 排班db manager
 * 
 * @author hjw
 * 
 */
public class TArrangementManager {

	// 添加:注意，这里的date要打引号！
	// insert into t_arrangement(doctor_id,room_id,expense,limit,date,num)
	// values ()
	public int add(TArrangement arrangement) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "insert into t_arrangement(doctor_id,room_id,expense,limit,date,num) values ("
				+ arrangement.getDoctorId() + "," + arrangement.getRoomId() + "," + arrangement.getExpense() + ","
				+ arrangement.getLimit() + ",'" + SystemUtil.formatDate(arrangement.getDate()) + "',"
				+ arrangement.getNum() + ")";
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 删除
	public int delete(TArrangement arrangement) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "delete from t_arrangement where id=" + arrangement.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 更新
	public int update(TArrangement arrangement) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "update t_arrangement set doctor_id=" + arrangement.getDoctorId() + ",room_id="
				+ arrangement.getRoomId() + ",expense=" + arrangement.getExpense() + ",limit=" + arrangement.getLimit()
				+ ",date=convert(datetime,'" + SystemUtil.formatDate(arrangement.getDate()) + "',120),num="
				+ arrangement.getNum() + " where id=" + arrangement.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 根据科室id，起止时间 查询所有排班，时间要打引号
	// select * from t_arrangement where room_id=1 and date between '2013/01/13'
	// and '2013/02/12'
	public int checkAvailable(TArrangement arrangement) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select count(*) as count from t_register where arrangement_id = " + arrangement.getId();
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		int count = 0;
		while (rs.next()) {
			count = rs.getInt("count");
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return count;
	}

	// 根据科室id，起止时间 查询所有排班，时间要打引号
	// select * from t_arrangement where room_id=1 and date between '2013/01/13'
	// and '2013/02/12'
	public ArrayList<TArrangement> searchAll(int roomid, Date fromDate, Date endDate) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_arrangement where room_id=" + roomid + " and date between '"
				+ SystemUtil.formatDate(fromDate) + "' and '" + SystemUtil.formatDate(endDate) + "'";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TArrangement> list = new ArrayList<TArrangement>();
		TArrangement arrangement = null;
		while (rs.next()) {
			arrangement = new TArrangement();
			setOne(arrangement, rs);
			list.add(arrangement);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 根据id查询
	public TArrangement searchByID(int id) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_arrangement where id=" + id;
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		TArrangement arrangement = null;
		while (rs.next()) {
			arrangement = new TArrangement();
			setOne(arrangement, rs);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return arrangement;
	}

	// 根据科室时间班次查询：
	public TArrangement searchByRoomDateNum(int roomid, Date date, int num) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_arrangement where room_id=" + roomid + " and date=" + SystemUtil.formatDate(date)
				+ " and num=" + num;
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		TArrangement arrangement = null;
		while (rs.next()) {
			arrangement = new TArrangement();
			setOne(arrangement, rs);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return arrangement;
	}

	// 根据科室
	public ArrayList<TTableGuahao> searchByRoom(int roomid) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select a.num as num,a.limit as limit,a.expense as expense,d.name as doctorName,a.id as aid from t_arrangement as a,t_room as r,t_doctor as d "
				+ "where a.doctor_id=d.id and a.room_id=r.id and DATEDIFF(DAY,a.date,GETDATE())=0 and r.id=" + roomid;
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TTableGuahao> tableGuahaoList = new ArrayList<TTableGuahao>();
		TTableGuahao item = null;
		while (rs.next()) {
			item = new TTableGuahao();
			setOneTableGuahao(item, rs);
			tableGuahaoList.add(item);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return tableGuahaoList;
	}

	private void setOneTableGuahao(TTableGuahao item, ResultSet rs) throws Exception {
		item.aid = rs.getInt("aid");
		item.doctorName = rs.getString("doctorName");
		item.expense = rs.getFloat("expense");
		item.limit = rs.getInt("limit");
		item.num = rs.getInt("num");
	}

	// 将数据库中的一条记录构造成一个对应的对象
	private void setOne(TArrangement arrangement, ResultSet rs) throws Exception {
		arrangement.setId(rs.getInt("id"));
		arrangement.setDoctorId(rs.getInt("doctor_id"));
		arrangement.setRoomId(rs.getInt("room_id"));
		arrangement.setDate(rs.getDate("date"));
		arrangement.setExpense(rs.getFloat("expense"));
		arrangement.setLimit(rs.getInt("limit"));
		arrangement.setNum(rs.getInt("num"));
	}

}
