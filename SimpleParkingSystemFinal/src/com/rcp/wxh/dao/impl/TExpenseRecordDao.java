package com.rcp.wxh.dao.impl;

import java.util.List;

import com.rcp.wxh.dao.inter.BaseHibernateDAO;

public class TExpenseRecordDao extends BaseHibernateDAO {

	public static final int CARDID = 1; // 根据卡号查询
	public static final int CARNUMBER = 2; // 根据车牌号码查询
	public static final int OWNERNAME = 3; // 根据车主姓名查询
	public static final int ALL = 4; // 查询所有

	/**
	 * 根据不同参数执行相应的查询
	 * 
	 * @param paras
	 * @param op
	 * @return
	 * @throws Exception
	 */
	public List<Object> getExpRecordByParas(Object[] paras, int op) throws Exception {
		List<Object> expRecord = null;
		String sql = null;
		switch (op) {
		case CARDID:
			sql = "from TExpenseRecord where TCard.cardid = ? and expensedate between ? and ? order by expensedate desc";
			break;
		case CARNUMBER:
			sql = "from TExpenseRecord where TCard.cardid in (select TCard.cardid from TMember where carnumber like ?) and expensedate between ? and ? order by expensedate desc";
			break;
		case OWNERNAME:
			sql = "from TExpenseRecord where TCard.cardid in (select TCard.cardid from TMember where name like ?) and expensedate between ? and ? order by expensedate desc";
			break;
		case ALL:
			sql = "from TExpenseRecord where expensedate between ? and ? order by expensedate desc";
		}
		try {
			expRecord = findByProperties(sql, paras);
		} catch (Exception er) {
			throw er;
		}
		return expRecord;
	}
}
