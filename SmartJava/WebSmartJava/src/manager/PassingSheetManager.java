package manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import tool.DataBaseTool;
import entity.PlacePrice;
import entity.Order;;

public class PassingSheetManager {
	//增加交接单
	public static int add(String sql){
		Connection dbConn = DataBaseTool.getConnection();
		int flag = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return flag;
	}

	// 增加订单信息
	public static int add(Order order) {
		//建立数据库连接
		Connection dbConn = DataBaseTool.getConnection();	
		//sql语句        
		String sql="";
		if(order.getUserId()==0)
		{
			sql="insert into pass values("+null+",";	
		}else {	
			sql="insert into orders values("+order.getUserId()+",";
		}
		sql=sql+"'"+order.getSender()+"'," +
				"'"+order.getsAddress()+"'," +
				"'"+order.getsCode()+"'," +
				"'"+order.getsPhone()+"'," +
				""+order.getsDistribution()+"," +
				"'"+order.getReceiver()+"'," +
				"'"+order.gettAddress()+"'," +
				"'"+order.gettCode()+"'," +
				"'"+order.gettPhone()+"'," +
				""+order.gettDistribution()+"," +
				"'"+order.getWorker()+"'," +
				"'"+order.getWorkDate()+"'," +
				""+order.getVolumn()+"," +
				""+order.getWeight()+"," +
				"'"+order.getPath()+"'," +
				""+order.getPrice()+"," +
				""+order.getStatus()+"," ;
				if(order.getCarId()==0)
				{		
					sql=sql+""+null+",";		
				}else {		
					sql=sql+""+order.getCarId()+",";	
				}
				if(order.getCurrentDistribution()==0)
				{
					sql=sql+""+null+",";				
				}else {	
					sql=sql+""+order.getCurrentDistribution()+",";	
				}					
				sql=sql+""+order.getGoodsType()+",'"+order.getRemark()+"')";
		
	
				
		
		int flag = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return flag;
	}

	// 根据id更新订单信息
	public static int update(Order order) {
		//建立数据库连接
		Connection dbConn = DataBaseTool.getConnection();
		//sql语句
		String sql="update orders set id=0 where 1=1";
		int flag = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return flag;
	}
	
	/*
	 * 根据两个hashMap中输入，进行部分更新
	 * hashMap:要修改的数据项及修改值
	 * hashMap2：查询待修改项的范围
	 */	
	public static int update(HashMap<String, String> hashMap,HashMap<String,String> hashMap2){		
		Connection dbConn = DataBaseTool.getConnection();
		StringBuffer sql = new StringBuffer("update orders set ");
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

	//根据id进行delete
	public static int delete(int id) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "delete from orders where id="+id+"";
		int flag = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return flag;
	}

	// 根据订单id进行查询-返回order
	public static Order searchById(int id) throws SQLException {
		//建立连接
		Connection dbConn = DataBaseTool.getConnection();
		//sql语句
		String sql = "select * from orders where id="+id+"";
		Order order=new Order();
		//执行sql语句
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		//结果存储
		while (rs.next()) {
			//int数据
			order.setId(rs.getInt("id"));
			order.setUserId(rs.getInt("userId"));
			order.setsDistribution(rs.getInt("sDistribution"));
			order.settDistribution(rs.getInt("tDistribution"));
			order.setCarId(rs.getInt("carId"));
			order.setCurrentDistribution(rs.getInt("currentDistribution"));
				
			//发件人
			order.setSender(rs.getString("sender"));
			order.setsCode(rs.getString("sCode"));
			order.setsAddress(rs.getString("sAddress"));
			order.setsPhone(rs.getString("sPhone"));
			
			//收件人
			order.setReceiver(rs.getString("receiver"));
			order.settCode(rs.getString("tCode"));
			order.settAddress(rs.getString("tAddress"));
			order.settPhone(rs.getString("tPhone"));	
	
			//订单信息
			order.setVolumn(rs.getFloat("volumn"));
			order.setWeight(rs.getFloat("weight"));
			order.setPrice(rs.getFloat("price"));
			order.setStatus(rs.getInt("status"));
			order.setGoodsType(rs.getInt("goodsType"));
			
			order.setRemark(rs.getString("remark"));		
			order.setWorker(rs.getString("worker"));
			order.setPath(rs.getString("path"));
			order.setWorkDate(rs.getString("workDate"));
		}
		//关闭连接
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		//返回数据
		return order;
	}

	
	//查询订单全部数据-返回链表
	public static ArrayList<Order> search(String sql) throws SQLException {
		//建立连接
		Connection dbConn = DataBaseTool.getConnection();
		//执行sql语句
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<Order> list = new ArrayList<Order>();
		//结果存储
		while (rs.next()) {
			Order order=new Order();
			//int数据
			order.setId(rs.getInt("id"));
			order.setUserId(rs.getInt("userId"));
			order.setsDistribution(rs.getInt("sDistribution"));
			order.settDistribution(rs.getInt("tDistribution"));
			order.setCarId(rs.getInt("carId"));
			order.setCurrentDistribution(rs.getInt("currentDistribution"));
				
			//发件人
			order.setSender(rs.getString("sender"));
			order.setsCode(rs.getString("sCode"));
			order.setsAddress(rs.getString("sAddress"));
			order.setsPhone(rs.getString("sPhone"));
			
			//收件人
			order.setReceiver(rs.getString("receiver"));
			order.settCode(rs.getString("tCode"));
			order.settAddress(rs.getString("tAddress"));
			order.settPhone(rs.getString("tPhone"));	
	
			//订单信息
			order.setVolumn(rs.getFloat("volumn"));
			order.setWeight(rs.getFloat("weight"));
			order.setPrice(rs.getFloat("price"));
			order.setStatus(rs.getInt("status"));
			order.setGoodsType(rs.getInt("goodsType"));
			
			order.setRemark(rs.getString("remark"));		
			order.setWorker(rs.getString("worker"));
			order.setPath(rs.getString("path"));
			order.setWorkDate(rs.getString("workDate"));
			list.add(order);
		}
		//关闭连接
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		//返回数据
		return list;
	}
	
	// 根据map进行search -- 组合查询 
	public static ArrayList<Order> searchByMap(HashMap<String, String> hashMap)
			throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		StringBuffer sql = new StringBuffer("select * from orders where 1=1 ");
		for (Entry<String, String> entry : hashMap.entrySet()) {
			sql.append("and " + entry.getKey() + "=" + entry.getValue() + " ");
		}
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql.toString());
		ArrayList<Order> list = new ArrayList<Order>();
		while (rs.next()) {
			Order od=new Order();
			od.setId(rs.getInt("id"));
			od.setUserId(rs.getInt("userId"));
			od.setSender(rs.getString("sender"));
			od.setsAddress(rs.getString("sAddress"));
			od.setsCode(rs.getString("sCode"));
			od.setsPhone(rs.getString("sPhone"));
			od.setsDistribution(rs.getInt("sDistribution"));
			od.setReceiver(rs.getString("receiver"));
			od.settAddress(rs.getString("tAddress"));
			od.settCode(rs.getString("tCode"));
			od.settPhone(rs.getString("tPhone"));
			od.settDistribution(rs.getInt("tDistribution"));
			od.setWorker(rs.getString("worker"));
			od.setWorkDate(rs.getString("workDate"));
			od.setVolumn(rs.getFloat("volumn"));
			od.setWeight(rs.getFloat("weight"));
			od.setPath(rs.getString("path"));
			od.setPrice(rs.getFloat("price"));
			od.setStatus(rs.getInt("status"));
			od.setCarId(rs.getInt("carId"));
			od.setCurrentDistribution(rs.getInt("currentDistribution"));
			od.setGoodsType(rs.getInt("goodsType"));											
			od.setRemark(rs.getString("remark"));
				list.add(od);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}	
}

