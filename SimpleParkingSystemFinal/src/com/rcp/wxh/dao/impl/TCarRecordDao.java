package com.rcp.wxh.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.rcp.wxh.dao.inter.BaseHibernateDAO;

/**
 * 停车记录 dao层
 * 
 * @author wuxuehong 2011-11-16
 * 
 */
public class TCarRecordDao extends BaseHibernateDAO {

	private static final Log log = LogFactory.getLog(TExceptionRecordDAO.class);

	public static final int ALL = 1; // 查询所有
	public static final int CARDID = 2; // 根据卡号查询
	public static final int CARNUMBER = 3; // 根据车牌号码查询
	public static final int OWNERNAME = 4; // 根据车主姓名查询

	// 在时间段内按日来统计
	public Object[][] getCarRecodebyTimearea_day(String timefrom, String timeto, int stoptype) throws Exception {
		Object[][] chart = null;
		String typestring;
		try {
			Session session = getSession(); // 开启会话
			Connection con = session.connection();
			if (stoptype == 0)// 0为出场
				typestring = "EXITTIME";
			else
				// 1为入场
				typestring = "ENTERTIME";
			PreparedStatement ps = con.prepareStatement("select count(ID) as c,DATE_FORMAT(" + typestring
					+ ",'%Y-%m-%d') as d ," + typestring + " from T_CAR_RECORD where DATE_FORMAT(" + typestring
					+ ",'%Y-%m-%d') between ? and ? group by d order by " + typestring);
			// ps.setInt(1,stoptype);
			ps.setString(1, timefrom);// 把起止时间放入SQL语句
			ps.setString(2, timeto);
			// System.out.println(ps.toString());
			ResultSet pss = ps.executeQuery();
			if (pss.next()) { // 如果结果不为空
				pss.last();
				chart = new Object[pss.getRow()][2];
				pss.beforeFirst();
				int i = 0;
				while (pss.next()) {
					chart[i][0] = pss.getInt("c");
					chart[i][1] = pss.getDate(typestring);// 把时间放入二维数组的第二排
					i++;
				}
			}
			closeSession(session); // 关闭会话
			ps.close();
			con.close();
			return chart;
		} catch (Exception re) {
			log.error("Delete object failed.");
			throw re;
		}
	}

	// 在时间段内按月来统计
	public Object[][] getCarRecodebyTimearea_month(String timefrom, String timeto, int stoptype) throws Exception {
		Object[][] chart = null;
		String typestring_month;
		try {
			Session session = getSession(); // 开启会话
			Connection con = session.connection();
			// System.out.println("dao层的数据来时"+timefrom+"去时："+timeto);
			// PreparedStatement ps = con.prepareStatement(
			// "select count(ID) as c,DATE_FORMAT(STOPTIME,'%Y-%m') as m ,STOPTIME " +
			// "from T_CAR_RECORD where stoptype = ? and " +
			// "DATE_FORMAT(STOPTIME,'%Y-%m') between ? and ? group by m");
			if (stoptype == 0)// 0为出场
				typestring_month = "EXITTIME";
			else
				// 1为入场
				typestring_month = "ENTERTIME";
			String query_str_a = "select count(ID) as c,DATE_FORMAT(" + typestring_month + ",'%Y-%m') as m , "
					+ typestring_month + " from T_CAR_RECORD where DATE_FORMAT(" + typestring_month
					+ ",'%Y-%m') between ? and ? group by m";
			// System.out.println(query_str_a);
			PreparedStatement ps = con.prepareStatement(query_str_a);
			// ps.setInt(1,stoptype);
			ps.setString(1, timefrom);// 这里timefrom,timeto传进来的应该是“2010-05”“2010-11”之类格式的
			ps.setString(2, timeto);// 格式为“2010-5”MYsql读不出来
			// System.out.println(ps.toString());
			ResultSet pss = ps.executeQuery();
			if (pss.next()) { // 如果结果不为空
				pss.last();
				chart = new Object[pss.getRow()][2];
				// System.out.println("看看多少："+pss.getRow());
				pss.beforeFirst();
				int i = 0;
				while (pss.next()) {
					chart[i][0] = pss.getInt("c");
					chart[i][1] = pss.getDate(typestring_month);// 把时间放入二维数组的第二排
					i++;
					// System.out.println(pss.getInt("c"));
				}
			}

			closeSession(session); // 关闭会话
			ps.close();
			con.close();
			return chart;
		} catch (Exception re) {
			log.error("Delete object failed.");
			throw re;
		}
	}

	/**
	 * 根据不同参数及查询指令查询车辆出入记录信息
	 * 
	 * @param paras
	 * @param op
	 * @return
	 * @throws Exception
	 */
	public List<Object> getCarRecordsByParas(Object[] paras, int op) throws Exception {
		List<Object> carRecords = null;
		String sql = null;
		switch (op) {
		case ALL:
			sql = "from TCarRecord where entertime between ? and ? order by entertime desc";
			break;
		case CARDID:
			sql = "from TCarRecord where entertime between ? and ? and TCard.cardid = ? order by entertime desc";
			break;
		case CARNUMBER:
			sql = "from TCarRecord where TCard.cardid in (select TCard.cardid from TMember where carnumber like ?) and entertime between ? and ? order by entertime desc";
			break;
		case OWNERNAME:
			sql = "from TCarRecord where TCard.cardid in (select TCard.cardid from TMember where name like ?) and entertime between ? and ? order by entertime desc";
			break;
		}
		try {
			carRecords = findByProperties(sql, paras);
		} catch (Exception e) {
			throw e;
		}
		return carRecords;
	}

}
