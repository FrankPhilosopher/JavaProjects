package cn.edu.csu.dbhospital.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import cn.edu.csu.dbhospital.pojo.TWorker;
import cn.edu.csu.dbhospital.util.MD5Util;

public class TWorkerManager {

	// 添加
	// insert into t_worker(name,realname,password,phone,gander,canModify,canStatics) values ('','','','',,,)
	public int add(TWorker worker) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "insert into t_worker(type,name,realname,password,phone,gander,canModify,canStatics) values ("
				+ TWorker.TYPE_OPERATOR + ",'" + worker.getName() + "','" + worker.getRealname() + "','"
				+ MD5Util.encrypt(worker.getPassword()) + "','" + worker.getPhone() + "'," + worker.getGander() + ","
				+ worker.getCanModify() + "," + worker.getCanStatics() + ")";
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 更新
	// update t_worker set name='小武',gander=1,realname='院长',password='',phone='',canModify=0,canStatics=0 where id=1
	public int update(TWorker worker) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "update t_worker set name='" + worker.getName() + "',gander=" + worker.getGander() + ",realname='"
				+ worker.getRealname() + "',password='" + MD5Util.encrypt(worker.getPassword()) + "',phone='" + worker.getPhone()
				+ "',canModify=" + worker.getCanModify() + ",canStatics=" + worker.getCanStatics() + " where id="
				+ worker.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 删除
	public int delete(TWorker worker) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "delete from t_worker where id=" + worker.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 查询所有
	public ArrayList<TWorker> searchAll() throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_worker";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TWorker> list = new ArrayList<TWorker>();
		TWorker worker = null;
		while (rs.next()) {
			worker = new TWorker();
			setOne(worker, rs);
			list.add(worker);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 根据名字查询
	public ArrayList<TWorker> searchByName(String name) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_worker where realname like '%" + name + "%'";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TWorker> workerList = new ArrayList<TWorker>();
		TWorker worker = null;
		while (rs.next()) {
			worker = new TWorker();
			setOne(worker, rs);
			workerList.add(worker);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return workerList;
	}

	// 登陆
	public TWorker loginByName(String name) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_worker where name='" + name + "'";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		TWorker worker = null;
		while (rs.next()) {
			worker = new TWorker();
			setOne(worker, rs);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return worker;
	}

	// 将数据库中的一条记录构造成一个对应的对象
	private void setOne(TWorker worker, ResultSet rs) throws Exception {
		worker.setId(rs.getInt("id"));
		worker.setName(rs.getString("name"));
		worker.setRealname(rs.getString("realname"));
		worker.setPassword(rs.getString("password"));
		worker.setPhone(rs.getString("phone"));
		worker.setCanModify(rs.getInt("canModify"));
		worker.setCanStatics(rs.getInt("canStatics"));
		worker.setGander(rs.getInt("gander"));
		worker.setRegtime(rs.getDate("regtime"));
		worker.setType(rs.getInt("type"));
	}

}
