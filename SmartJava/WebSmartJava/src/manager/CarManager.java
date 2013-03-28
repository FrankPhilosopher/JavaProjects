package manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import tool.DataBaseTool;
import entity.Car;

public class CarManager {

	// 添加Car
	public static int add(Car car) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "insert into car(number,driver,weight,volumn,currentDistribution,status,currentWeight,currentVolumn) values('"
				+ car.getNumber()
				+ "', '"
				+ car.getDriver()
				+ "','"
				+ car.getWeight()
				+ "','"
				+ car.getVolumn()
				+ "','"
				+ car.getCurrentDistribution() + "','" + car.getStatus() + "','"+car.getCurrentWeight()+"','"+car.getCurrentVolumn()+"')";

		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}
	/*
	 * 根据两个hashMap中输入，进行部分更新
	 * hashMap:要修改的数据项及修改值
	 * hashMap2：查询待修改项的范围
	 */	
	public static int update(HashMap<String, String> hashMap,HashMap<String,String> hashMap2){		
		Connection dbConn = DataBaseTool.getConnection();
		StringBuffer sql = new StringBuffer("update car set ");
		for (Entry<String, String> entry : hashMap.entrySet()) {
			sql.append(entry.getKey()+ "='" + entry.getValue()+"',");
		}
		sql.delete(sql.length()-1,sql.length());
		sql.append(" where ");
		for (Entry<String, String> entry : hashMap2.entrySet()) {
			sql.append(entry.getKey()+ "='" + entry.getValue()+"' and");
		}
		sql.delete(sql.length()-4,sql.length());
		int result=DataBaseTool.executeSQL(dbConn, sql.toString());
		return result;
	}
	// 根据car进行update
	public static int update(Car car) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = " update car set number='" + car.getNumber()
				+ "',driver='" + car.getDriver() + "',weight='"
				+ car.getWeight() + "',volumn=" + car.getVolumn()
				+ ",currentDistribution=" + car.getCurrentDistribution()
				+ ",status=" + car.getStatus() + " where id=" + car.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 根据id进行delete
	public static int delete(int carid) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "delete from car where id=" + carid;
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 根据map进行search -- 组合查询
	public  static ArrayList<Car> searchByMap(HashMap<String, String> hashMap)
			throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		StringBuffer sql = new StringBuffer("select * from car where 1=1 ");
		for (Entry<String, String> entry : hashMap.entrySet()) {
			sql.append("and " + entry.getKey() + "='" + entry.getValue() + "' ");
		}
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql.toString());
		Car car = null;
		ArrayList<Car> list = new ArrayList<Car>();
		while (rs.next()) {
			car = new Car();
			car.setId(rs.getInt("id"));
			car.setNumber(rs.getString("number"));
			car.setDriver(rs.getString("driver"));
			car.setWeight(rs.getFloat("weight"));
			car.setVolumn(rs.getFloat("volumn"));
			car.setCurrentDistribution(rs.getInt("currentDistribution"));
			car.setStatus(rs.getInt("status"));
			car.setCurrentWeight(rs.getFloat("currentWeight"));
			car.setCurrentVolumn(rs.getFloat("currentVolumn"));
			list.add(car);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 根据id进行search
	public static Car searchById(int id) throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from car where id=" + id;
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		Car car = null;
		while (rs.next()) {
			car = new Car();
			car.setId(rs.getInt("id"));
			car.setNumber(rs.getString("number"));
			car.setDriver(rs.getString("driver"));
			car.setWeight(rs.getFloat("weight"));
			car.setVolumn(rs.getFloat("volumn"));
			car.setCurrentDistribution(rs.getInt("currentDistribution"));
			car.setStatus(rs.getInt("status"));
			car.setCurrentWeight(rs.getFloat("currentWeight"));
			car.setCurrentVolumn(rs.getFloat("currentVolumn"));
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return car;
	}

	// 全部search
	public static ArrayList<Car> search() throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from car";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<Car> list = new ArrayList<Car>();
		while (rs.next()) {
			Car car = new Car();
			car.setId(rs.getInt("id"));
			car.setNumber(rs.getString("number"));
			car.setDriver(rs.getString("driver"));
			car.setWeight(rs.getFloat("weight"));
			car.setVolumn(rs.getFloat("volumn"));
			car.setCurrentDistribution(rs.getInt("currentDistribution"));
			car.setStatus(rs.getInt("status"));
			car.setCurrentWeight(rs.getFloat("currentWeight"));
			car.setCurrentVolumn(rs.getFloat("currentVolumn"));
			list.add(car);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}
}
