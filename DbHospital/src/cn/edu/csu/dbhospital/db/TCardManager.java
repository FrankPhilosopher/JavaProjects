package cn.edu.csu.dbhospital.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import cn.edu.csu.dbhospital.pojo.TCard;

public class TCardManager {

	// 添加
	public int add(TCard card) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "insert into t_card(cardnum,user_id,dealed) values ('"
				+ card.getCardnum() + "'," + card.getUserId() + ","
				+ card.getDealed() + ")";
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 更新
	public int update(TCard card) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "update t_card set cardnum='" + card.getCardnum()
				+ "',user_id=" + card.getUserId() + ",dealed="
				+ card.getDealed() + " where id=" + card.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 删除
	public int delete(TCard card) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "delete from t_card where id=" + card.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 查询所有
	public ArrayList<TCard> searchAll() throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_card";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TCard> list = new ArrayList<TCard>();
		TCard card = null;
		while (rs.next()) {
			card = new TCard();
			setOne(card, rs);
			list.add(card);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 根据卡序号查询
	public ArrayList<TCard> searchByCardnum(String cardnum) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_card where cardnum like '%" + cardnum
				+ "%'";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<TCard> userList = new ArrayList<TCard>();
		TCard card = null;
		while (rs.next()) {
			card = new TCard();
			setOne(card, rs);
			userList.add(card);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return userList;
	}

	// 根据用户id查询
	public TCard loginByUserId(int userid) throws Exception {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from t_card where user_id=" + userid + "";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		TCard card = null;
		while (rs.next()) {
			card = new TCard();
			setOne(card, rs);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return card;
	}

	// 将数据库中的一条记录构造成一个对应的对象
	private void setOne(TCard card, ResultSet rs) throws Exception {
		card.setId(rs.getInt("id"));
		card.setCardnum(rs.getString("cardnum"));
		card.setDealed(rs.getInt("dealed"));
		card.setUserId(rs.getInt("user_id"));
		card.setDatetime(rs.getDate("datetime"));
	}

}
