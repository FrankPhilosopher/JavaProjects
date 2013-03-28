package manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tool.DataBaseTool;
import entity.ExcelItem;

public class ExcelManager {

	// 调用存储过程 ProcExcel
	public ArrayList<ExcelItem> callProcExcel(int type, int year, int season,
			int month, int distributionId) throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		CallableStatement statement = dbConn
				.prepareCall("{call proc_excel(?,?,?,?,?)}");
		statement.setInt(1, type);
		statement.setInt(2, year);
		statement.setInt(3, season);
		statement.setInt(4, month);
		statement.setInt(5, distributionId);
		ResultSet rs = statement.executeQuery();
		ArrayList<ExcelItem> list = new ArrayList<ExcelItem>();
		while (rs.next()) {
			ExcelItem item = new ExcelItem();
			try {
				setOneItem(item, rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(item);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 内部方法---从、数据库中取出一个user放置数据到user中
	private void setOneItem(ExcelItem item, ResultSet rs) throws Exception {
		item.setName(rs.getString("name"));
		item.setPrice(rs.getFloat("price"));
		item.setWeight(rs.getFloat("weight"));
		item.setVolumn(rs.getFloat("volumn"));
	}

}
