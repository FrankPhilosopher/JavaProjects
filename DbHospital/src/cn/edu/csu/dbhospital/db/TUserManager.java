package cn.edu.csu.dbhospital.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import cn.edu.csu.dbhospital.pojo.TUser;
import cn.edu.csu.dbhospital.pojo.TWorker;
import cn.edu.csu.dbhospital.util.MD5Util;

public class TUserManager {

	// 添加
	public int add(TUser user) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "insert into t_user(name,realname,password,phone,gander,card,cardnum) values ('" + user.getName()
				+ "','" + user.getRealname() + "','" + MD5Util.encrypt(user.getPassword()) + "','" + user.getPhone()
				+ "'," + user.getGander() + ",'" + user.getCard() + "','" + user.getCardnum() + "')";
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 添加诊疗卡时添加用户
	public int addCardUser(TUser user) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "insert into t_user(realname,phone,gander,card,cardnum) values ('" + user.getRealname() + "','"
				+ user.getPhone() + "'," + +user.getGander() + ",'" + user.getCard() + "','" + user.getCardnum() + "')";
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 更新 --- 更新时密码不进行加密操作，由前面完成
	public int update(TUser user) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "update t_user set name='" + user.getName() + "',gander=" + user.getGander() + ",realname='"
				+ user.getRealname() + "',password='" + user.getPassword() + "',phone='" + user.getPhone() + "',card='"
				+ user.getCard() + "',cardnum='" + user.getCardnum() + "' where id=" + user.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 删除
	public int delete(int userid) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "delete from t_user where id=" + userid;
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 查询所有
	public ArrayList<TUser> searchAll() throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_user";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TUser> list = new ArrayList<TUser>();
		TUser user = null;
		while (rs.next()) {
			user = new TUser();
			setOne(user, rs);
			list.add(user);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 根据名字查询
	public ArrayList<TUser> searchByName(String name) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_user where realname like '%" + name + "%'";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TUser> userList = new ArrayList<TUser>();
		TUser user = null;
		while (rs.next()) {
			user = new TUser();
			setOne(user, rs);
			userList.add(user);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return userList;
	}

	// 登陆
	public TUser loginByName(String name) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_user where name='" + name + "'";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		TUser user = null;
		while (rs.next()) {
			user = new TUser();
			setOne(user, rs);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return user;
	}

	// 根据id查询
	public TUser searchById(int id) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_user where id=" + id + "";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		TUser user = null;
		while (rs.next()) {
			user = new TUser();
			setOne(user, rs);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return user;
	}

	// 根据card查询
	public TUser searchByCardnum(String card) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_user where cardnum='" + card + "'";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		TUser user = null;
		while (rs.next()) {
			user = new TUser();
			setOne(user, rs);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return user;
	}

	// 将数据库中的一条记录构造成一个对应的对象
	private void setOne(TUser user, ResultSet rs) throws Exception {
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setRealname(rs.getString("realname"));
		user.setPassword(rs.getString("password"));
		user.setPhone(rs.getString("phone"));
		user.setCard(rs.getString("card"));
		user.setGander(rs.getInt("gander"));
		user.setRegtime(rs.getDate("regtime"));
		user.setCardnum(rs.getString("cardnum"));
	}

}
