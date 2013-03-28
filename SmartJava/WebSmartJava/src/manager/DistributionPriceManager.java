package manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import tool.DataBaseTool;
import entity.DistributionPrice;

public class DistributionPriceManager {
	// 添加配送点间的价格
	public int add(DistributionPrice dPrice) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "insert into distributionPrice (sDistribution,tDistribution,firstKilo,secondKilo) values("
				+ dPrice.getsDistribution()
				+ ","
				+ dPrice.getDistribution()
				+ ","
				+ dPrice.getFirstKilo()
				+ ","
				+ dPrice.getSecondKilo()
				+ ")";

		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 根据map进行update
	public int updateById(String id, String firstKilo, String secondKilo)
			throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "update distributionPrice set firstKilo='" + firstKilo
				+ "',secondKilo='" + secondKilo + "' where id=" + id;
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 根据dPrice对象进行update
	public int update(DistributionPrice dPrice) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "update distributionPrice set firstKilo="
				+ dPrice.getFirstKilo() + ",secondKilo="
				+ dPrice.getSecondKilo() + " where id=" + dPrice.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 根据id进行delete
	public int delete(int carid) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "delete from distributionPrice where id=" + carid;
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 根据map进行search -- 组合查询
	public ArrayList<DistributionPrice> searchByMap(
			HashMap<String, String> hashMap) throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		StringBuffer sql = new StringBuffer(
				"select * from distributionPrice where 1=1 ");
		for (Entry<String, String> entry : hashMap.entrySet()) {
			sql.append("and " + entry.getKey() + "=" + entry.getValue() + " ");
		}
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql.toString());
		DistributionPrice dPrice = null;
		ArrayList<DistributionPrice> list = new ArrayList<DistributionPrice>();
		while (rs.next()) {
			dPrice = new DistributionPrice();
			dPrice.setId(rs.getInt("id"));
			dPrice.setsDistribution(rs.getInt("sDistribution"));
			dPrice.setDistribution(rs.getInt("tDistribution"));
			dPrice.setFirstKilo(rs.getFloat("firstKilo"));
			dPrice.setSecondKilo(rs.getFloat("secondKilo"));

			list.add(dPrice);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 根据id进行search
	public DistributionPrice searchById(int id) throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from distributionPrice where id=" + id;
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		DistributionPrice dPrice = null;
		while (rs.next()) {
			dPrice = new DistributionPrice();
			dPrice.setId(rs.getInt("id"));
			dPrice.setsDistribution(rs.getInt("sDistribution"));
			dPrice.setDistribution(rs.getInt("tDistribution"));
			dPrice.setFirstKilo(rs.getFloat("firstKilo"));
			dPrice.setSecondKilo(rs.getFloat("secondKilo"));
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return dPrice;
	}

	// 全部search
	public ArrayList<DistributionPrice> search() throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from distributionPrice";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<DistributionPrice> list = new ArrayList<DistributionPrice>();
		DistributionPrice dPrice = null;
		while (rs.next()) {
			dPrice = new DistributionPrice();
			dPrice.setId(rs.getInt("id"));
			dPrice.setsDistribution(rs.getInt("sDistribution"));
			dPrice.setDistribution(rs.getInt("tDistribution"));
			dPrice.setFirstKilo(rs.getFloat("firstKilo"));
			dPrice.setSecondKilo(rs.getFloat("secondKilo"));

			list.add(dPrice);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}
}
