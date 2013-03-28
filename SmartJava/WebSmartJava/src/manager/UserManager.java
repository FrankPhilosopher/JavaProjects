package manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import tool.DataBaseTool;
import entity.User;

public class UserManager {

	// 添加User
	public int add(User user) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "insert into users(name,password,realname,phone,distributionId,companyId,priority) values('"
				+ user.getName()
				+ "','"
				+ user.getPassword()
				+ "','"
				+ user.getRealname() + "','" + user.getPhone() + "'";
		if (user.getDistributionId() == 0) {
			sql += ",null";
		} else {
			sql += ("," + user.getDistributionId());
		}
		if (user.getCompanyId() == 0) {
			sql += ",null";
		} else {
			sql += ("," + user.getCompanyId());
		}
		sql += "," + user.getPriority() + ")";
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 根据user进行update
	public int update(User user) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "update users set name='" + user.getName()
				+ "',password='" + user.getPassword() + "',realname='"
				+ user.getRealname() + "',phone='" + user.getPhone() + "'";

		if (user.getDistributionId() == 0) {
			sql += ",distributionId=null";
		} else {
			sql += (",distributionId=" + user.getDistributionId());
		}
		if (user.getCompanyId() == 0) {
			sql += ",companyId=null";
		} else {
			sql += (",companyId=" + user.getCompanyId());
		}
		sql += ",priority=" + user.getPriority() + " where id=" + user.getId();

		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 根据id进行delete
	public int delete(int userid) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "delete from users where id=" + userid;
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	public int del(int Id) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "delete from users where distributionId =" + Id;
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 根据map进行search -- 组合查询
	public ArrayList<User> searchByMap(HashMap<String, String> hashMap)
			throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		StringBuffer sql = new StringBuffer("select * from users where 1=1 ");
		for (Entry<String, String> entry : hashMap.entrySet()) {
			sql.append("and " + entry.getKey() + "=" + entry.getValue() + " ");
		}
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql.toString());
		User user = null;
		ArrayList<User> list = new ArrayList<User>();
		while (rs.next()) {
			user = new User();
			try {
				setOneUser(user, rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(user);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 根据sql进行search
	public User searchBySql(String sql) throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		User user = null;
		while (rs.next()) {
			user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setRealname(rs.getString("realname"));
			user.setPhone(rs.getString("phone"));
			user.setDistributionId(rs.getInt("distributionId"));
			user.setCompanyId(rs.getInt("companyId"));
			user.setPriority(rs.getInt("priority"));
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return user;
	}

	// 根据id进行search
	public User searchById(int id) throws SQLException {
		String sql = "select * from users where id=" + id;
		User user = searchBySql(sql);
		return user;
	}

	// 根据配送点id搜索
	public User searchByditributionId(int id) throws SQLException {
		String sql = "select * from users where priority="
				+ User.DISTRIBUTION_ADMIN + " and distributionId=" + id;
		User user = searchBySql(sql);
		return user;
	}

	// 搜索所有的配送点管理员
	public ArrayList searchInId() throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from users where priority="
				+ User.DISTRIBUTION_ADMIN
				+ " and distributionId in (select id from distribution)";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList list = new ArrayList();
		User user = null;
		while (rs.next()) {
			user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setRealname(rs.getString("realname"));
			user.setPhone(rs.getString("phone"));
			user.setDistributionId(rs.getInt("distributionId"));
			user.setCompanyId(rs.getInt("companyId"));
			user.setPriority(rs.getInt("priority"));
			list.add(user);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 根据name进行search
	public User searchByName(String name) throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from users where name='" + name + "'";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		User user = null;
		while (rs.next()) {
			user = new User();
			try {
				setOneUser(user, rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return user;
	}

	// 内部方法---从、数据库中取出一个user放置数据到user中
	private void setOneUser(User user, ResultSet rs) throws Exception {
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setRealname(rs.getString("realname"));
		user.setPassword(rs.getString("password"));
		user.setPhone(rs.getString("phone"));
		user.setCompanyId(rs.getInt("companyId"));
		user.setDistributionId(rs.getInt("distributionId"));
		user.setPriority(rs.getInt("priority"));
	}

	// 全部search
	public ArrayList<User> search() throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from users";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<User> list = new ArrayList<User>();
		while (rs.next()) {
			User user = new User();
			try {
				setOneUser(user, rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(user);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 配送点-查询user-模糊查询
	// type = 0 表示无条件全查出来，type=1表示有条件
	public ArrayList<User> searchDistributionUser(int did, int type,
			String key, String value) throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = null;
		if (type == 0) {
			sql = "select * from users where distributionId=" + did
					+ " and priority=" + User.DISTRIBUTION_WORKER;
		} else {
			sql = "select * from users where distributionId=" + did
					+ " and priority=" + User.DISTRIBUTION_WORKER + " and "
					+ key + " like '%" + value + "%'";
		}
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<User> list = new ArrayList<User>();
		while (rs.next()) {
			User user = new User();
			try {
				setOneUser(user, rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(user);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 用来测试存储过程的 hjw
	public ArrayList<User> call(int priority) throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		CallableStatement statement = dbConn.prepareCall("{call proc_selectUsers(?)}");
		statement.setInt(1, priority);
		ResultSet rs = statement.executeQuery();
		ArrayList<User> list = new ArrayList<User>();
		while (rs.next()) {
			User user = new User();
			try {
				setOneUser(user, rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(user);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}
}
