package cn.edu.csu.dbhospital.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import cn.edu.csu.dbhospital.pojo.TDirectory;

/**
 * 字典db manager
 * 
 * @author hjw
 * 
 */
public class TDirectoryManager {

	// 添加
	public int add(TDirectory directory) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "insert into t_directory(name,value) values ('" + directory.getName() + "','"
				+ directory.getValue() + "')";
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 删除
	public int delete(TDirectory directory) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "delete from t_directory where id=" + directory.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 更新---根据名称更新
	public int update(TDirectory directory) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "update t_directory set name='" + directory.getName() + "' , value='" + directory.getValue()
				+ "' where name='" + directory.getName() + "' ";
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 查询所有
	public ArrayList<TDirectory> searchAll() throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_directory";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TDirectory> list = new ArrayList<TDirectory>();
		TDirectory directory = null;
		while (rs.next()) {
			directory = new TDirectory();
			setOne(directory, rs);
			list.add(directory);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 根据id查询
	public TDirectory searchByID(int id) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_directory where id=" + id;
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		TDirectory directory = null;
		while (rs.next()) {
			directory = new TDirectory();
			setOne(directory, rs);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return directory;
	}

	// 根据名字查询
	public ArrayList<TDirectory> searchByName(String name) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_directory where name='" + name + "' ";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TDirectory> list = new ArrayList<TDirectory>();
		TDirectory directory = null;
		while (rs.next()) {
			directory = new TDirectory();
			setOne(directory, rs);
			list.add(directory);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 将数据库中的一条记录构造成一个对应的对象
	private void setOne(TDirectory directory, ResultSet rs) throws Exception {
		directory.setId(rs.getInt("id"));
		directory.setName(rs.getString("name"));
		directory.setValue(rs.getString("value"));
	}

}
