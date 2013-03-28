package manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import tool.DataBaseTool;
import entity.PlacePrice;

public class PlacePriceManager {

	// 增加价格和范围信息
	public static int add(PlacePrice pp) {
		//建立数据库连接
		Connection dbConn = DataBaseTool.getConnection();	
		//sql语句
		String sql="insert into placePrice values("+pp.getDistributionId()+"," +
												"'"+pp.getPlaceName()+"'," +
												""+pp.getPlacePrice()+"," +
												"'"+pp.getPlaceTime()+"'," +
												"'"+pp.getRemark()+"')";
		int flag = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return flag;
	}

	// 根据id更新价格和范围信息
	public static int update(PlacePrice pp) {
		//建立数据库连接
		Connection dbConn = DataBaseTool.getConnection();
		//sql语句
		String sql="update placePrice set "+
				"placePrice="+pp.getPlacePrice()+"," +
				"placeTime='"+pp.getPlaceTime()+"'," +
				"remark='"+pp.getRemark()+"' where id="+pp.getId()+"";
		int flag = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return flag;
	}

	//根据id进行delete
	public static int delete(int id) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "delete from placePrice where id="+id+"";
		int flag = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return flag;
	}

	// 根据map进行search -- 组合查询 
	public ArrayList<PlacePrice> searchByMap(HashMap<String, String> hashMap)
			throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		StringBuffer sql = new StringBuffer("select * from placePrice where 1=1 ");
		for (Entry<String, String> entry : hashMap.entrySet()) {
			sql.append("and " + entry.getKey() + "=" + entry.getValue() + " ");
		}
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql.toString());
		ArrayList<PlacePrice> list = new ArrayList<PlacePrice>();
		while (rs.next()) {
			PlacePrice pp=new PlacePrice();
			pp.setDistributionId(rs.getInt("distributionId"));      
			pp.setId(rs.getInt("id")); 
			pp.setPlaceName(rs.getString("placeName"));         
			pp.setPlacePrice(rs.getFloat("placePrice"));   
			pp.setPlaceTime(rs.getString("placeTime"));
			pp.setRemark(rs.getString("remark"));	
			list.add(pp);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 根据价格和范围id进行search
	public static PlacePrice searchById(int id) throws SQLException {
		//建立连接
		Connection dbConn = DataBaseTool.getConnection();
		//sql语句
		String sql = "select * from placePrice where id="+id+"";
		PlacePrice pp=new PlacePrice();
		//执行sql语句
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		//结果存储
		while (rs.next()) {
			pp.setDistributionId(rs.getInt("distributionId"));      
			pp.setId(rs.getInt("id")); 
			pp.setPlaceName(rs.getString("placeName"));         
			pp.setPlacePrice(rs.getFloat("placePrice"));   
			pp.setPlaceTime(rs.getString("placeTime"));
			pp.setRemark(rs.getString("remark"));	
		}
		//关闭连接
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		//返回数据
		return pp;
	}

	
	//查询全部数据
	public ArrayList<PlacePrice> search(String sql) throws SQLException {
		//建立连接
		Connection dbConn = DataBaseTool.getConnection();
		//执行sql语句
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<PlacePrice> list = new ArrayList<PlacePrice>();
		//结果存储
		while (rs.next()) {
			PlacePrice pp=new PlacePrice();
			pp.setDistributionId(rs.getInt("distributionId"));      
			pp.setId(rs.getInt("id")); 
			pp.setPlaceName(rs.getString("placeName"));         
			pp.setPlacePrice(rs.getFloat("placePrice"));   
			pp.setPlaceTime(rs.getString("placeTime"));
			pp.setRemark(rs.getString("remark"));	
			list.add(pp);
		}
		//关闭连接
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		//返回数据
		return list;
	}
}
