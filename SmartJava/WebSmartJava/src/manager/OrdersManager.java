package manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import tool.DataBaseTool;
import entity.Orders;

public class OrdersManager {
	


	// 添加Order
	public int add(Orders orders) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "insert orders(receiver,tAddress,tPhone,tCode,sender,sAddress,sPhone,sCode,weight,volumn,quantity,goodsType,remark,price) values(" +
				"'"
				+ orders.getReceiver()
				+ "','"
				+ orders.gettAddress()
				+ "','"
				+ orders.gettPhone()
				+ "','"
				+ orders.gettCode() + "','"
				+ orders.getSender() + "','"
				+ orders.getsAddress() + "','"
				+ orders.getsPhone()+ "','"
				+ orders.getsCode() + "',"
				+ orders.getWeight() + ","
				+ orders.getVolumn() + ","
				+ orders.getQuantity()+ "," +
				"'" + orders.getGoodsType()
				+ "','" + orders.getRemark() + "',"
				+ orders.getPrice() + ")";
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 根据id进行update
	public int update(Orders orders) {
	Connection dbConn = DataBaseTool.getConnection();
	String sql = "update orders set receiver = '"
				+ orders.getReceiver()
				+ "',tAddress = '"
				+ orders.gettAddress()
				+ "',tPhone = '"
				+ orders.gettPhone()
				+ "',tCode = '"
				+ orders.gettCode() + "',sender = '"
				+ orders.getSender() + "',sAddress = '"
				+ orders.getsAddress() + "',sPhone = '"
				+ orders.getsPhone()+ "',sCode = '"
				+ orders.getsCode() + "',weight = "
				+ orders.getWeight() + ",volumn = "
				+ orders.getVolumn() + ",quantity = "
				+ orders.getQuantity()+ ",goodsType = '" + orders.getGoodsType() + "',peice = '" + orders.getRemark() + "',"
				+ orders.getPrice() + "where id ="+orders.getId();
		
				int count = DataBaseTool.executeSQL(dbConn, sql);
				DataBaseTool.close(null, null, dbConn);
				return count;
}

	// 根据id进行delete
	public int delete(Orders orders) {
	Connection dbConn = DataBaseTool.getConnection();
	String sql = "delete from orders where id="+orders.getId()+"";
	int count = DataBaseTool.executeSQL(dbConn, sql);
	DataBaseTool.close(null, null, dbConn);
	return count;
	}

	// 根据id进行search
		public Orders searchById(int id) throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from orders where id="+id+"";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		Orders orders = null;
		while (rs.next()) {
		if (rs.getInt("id") == id) {
		orders = new Orders();
		orders.setId(rs.getInt("id"));
		orders.setUserId(rs.getInt("userId"));
		orders.setReceiver(rs.getString("receiver"));
	
		orders.settAddress(rs.getString("tAddress"));
		orders.setSender(rs.getString("sender"));
	
		orders.setVolumn(rs.getInt("volumn"));
		orders.setWeight(rs.getInt("weight"));
		orders.setStatus(rs.getInt("status"));
			}
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return orders;
	}

	// 进行全部订单search
	public ArrayList search(String sql) throws SQLException {
	Connection dbConn = DataBaseTool.getConnection();
	
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList list = new ArrayList();
		while (rs.next()) {
		Orders orders = new Orders();
		orders.setId(rs.getInt("id"));
		orders.setUserId(rs.getInt("userId"));
		orders.setReceiver(rs.getString("receiver"));
	
		orders.settAddress(rs.getString("tAddress"));
		orders.setSender(rs.getString("sender"));
		
		orders.setVolumn(rs.getInt("volumn"));
		orders.setWeight(rs.getInt("weight"));
		orders.setStatus(rs.getInt("status"));
	
		list.add(orders);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}
}
