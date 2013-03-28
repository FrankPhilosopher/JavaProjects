package com.rcp.wxh.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rcp.wxh.dao.inter.BaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for TCard
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.rcp.wxh.dao.TCard
 * @author MyEclipse Persistence Tools
 */

public class TCardDao extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(TCardDao.class);
	// property constants
	public static final int CARDTYPEID = 1; // 根据卡片类型查 询 （小类）
	public static final int CARDSTATUS = 2; // 根据卡片状态查询
	public static final int CARDID_AND_TYPE = 4; // 根据卡片编号 以及 类型查询
	public static final int CARNUMBGER_AND_TYPE = 5; // 根据车牌号码 以及卡片类型查询 支持模糊查询
	public static final int CARNUMBER = 6; // 根据车牌号查询 支持模糊查询
	public static final int OWNERNAME_AND_TYPE = 7; // 根据车主姓名和类型查询 支持模糊查询
	public static final int OWNERNAME = 8; // 根据车主姓名查询 支持模糊查询

	/**
	 * 根据参数(类型ID 状态 次数)查询 返回卡集合
	 * 
	 * @param para
	 *            ,op
	 * @throws Exception
	 */
	public List<Object> getCardByPara(Object[] para, int op) throws Exception {
		List<Object> card = null;
		String sql = null;
		switch (op) {
		case CARDTYPEID:
			sql = "from TCard where cardtypeid=?";
			break;
		case CARDSTATUS:
			sql = "from TCard where cardstatus=?";
			break;
		case CARDID_AND_TYPE:
			sql = "from TCard where cardid=? and cardtypeid=?";
			break;
		case CARNUMBGER_AND_TYPE:
			sql = "from TCard where cardid in (select TCard.cardid from TMember where carnumber like ?) and cardtypeid=?";
			break;
		case CARNUMBER:
			sql = "from TCard where cardid in (select TCard.cardid from TMember where carnumber like ?)";
			break;
		case OWNERNAME:
			sql = "from TCard where cardid in (select TCard.cardid from TMember where name like ?)";
			break;
		case OWNERNAME_AND_TYPE:
			sql = "from TCard where cardid in (select TCard.cardid from TMember where name like ?) and cardtypeid=?";
			break;
		}
		try {
			card = findByProperties(sql, para);
		} catch (Exception er) {
			throw er;
		}
		return card;
	}

}