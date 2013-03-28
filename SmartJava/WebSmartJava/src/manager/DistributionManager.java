package manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import tool.DataBaseTool;
import entity.Distribution;

/**
 * 配送点数据库管理类
 * 
 * @author hjw
 * 
 */
public class DistributionManager {

	// 添加Distribution
	public static int add(Distribution distribution) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "insert into distribution(name,address) values('"
				+ distribution.getName() + "','" + distribution.getAddress()+"')";
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 根据distribution进行update
	public static int update(Distribution distribution) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "update distribution set name='" + distribution.getName()
				+ "',address='" + distribution.getAddress() + "' where id="
				+ distribution.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 根据id进行delete
	public static int delete(int distributionid) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "delete from distribution where id=" + distributionid;
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 根据map进行search -- 组合查询
	public static ArrayList<Distribution> searchByMap(HashMap<String, String> hashMap)
			throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		StringBuffer sql = new StringBuffer(
				"select * from distribution where 1=1 ");
		for (Entry<String, String> entry : hashMap.entrySet()) {
			sql.append("and " + entry.getKey() + "=" + entry.getValue() + " ");
		}
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql.toString());
		Distribution distribution = null;
		ArrayList<Distribution> list = new ArrayList<Distribution>();
		while (rs.next()) {
			distribution = new Distribution();
			distribution.setId(rs.getInt("id"));
			distribution.setName(rs.getString("name"));
			distribution.setAddress(rs.getString("address"));
			list.add(distribution);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 根据id进行search
	public static Distribution searchById(int id) throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from distribution where id='" + id+"'";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		Distribution distribution = null;
		while (rs.next()) {
				distribution = new Distribution();
				distribution.setId(rs.getInt("id"));
				distribution.setName(rs.getString("name"));
				distribution.setAddress(rs.getString("address"));
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return distribution;
	}

	// 根据name进行search
	public static Distribution searchByName(String name) throws SQLException {
		Distribution distribution = null;
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from distribution where name='"+name+"'" ;
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		while(rs.next()){
			distribution =new Distribution();
			distribution.setId(rs.getInt("id"));
			distribution.setName(rs.getString("name"));
			distribution.setAddress(rs.getString("address"));
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return distribution;
	}
	
	//根据地址搜索配送点
	public static Distribution searchByAddress(String address) throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from distribution where address='"+address+"'";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		Distribution distribution =null;  
		while(rs.next()){
			distribution =new Distribution();
			distribution.setId(rs.getInt("id"));
			distribution.setName(rs.getString("name"));
			distribution.setAddress(rs.getString("address"));
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return distribution;
	}

	// 全部search
	public static ArrayList<Distribution> search() throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from distribution";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<Distribution> list = new ArrayList<Distribution>();
		while (rs.next()) {
			Distribution distribution = new Distribution();
			distribution.setId(rs.getInt("id"));
			distribution.setName(rs.getString("name"));
			distribution.setAddress(rs.getString("address"));
			list.add(distribution);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}
}
