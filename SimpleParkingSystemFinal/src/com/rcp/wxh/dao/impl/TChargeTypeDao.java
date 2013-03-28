package com.rcp.wxh.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rcp.wxh.dao.inter.BaseHibernateDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * TChargeType entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see temp.TChargeType
 * @author MyEclipse Persistence Tools
 */

public class TChargeTypeDao extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(TChargeTypeDao.class);
	// property constants
	public static final String NAME = "name";
	public static final String TYPE = "type";
	public static final String FEE = "fee";
	public static final String MINUTE = "minute";
	public static final String OVERMINUTE = "overminute";
	public static final String HOUR = "hour";
	public static final String OVERHOUR = "overhour";
	public static final String TIMES = "times";
	public static final String REMARK = "remark";

	/**
	 * 根据收费方式名称查询收费方式
	 * 
	 * @param name
	 * @throws Exception
	 */
	public List<Object> getChargeTypeByName(String name) throws Exception {
		List<Object> chargeType = null;
		String sql = "from TChargeType where name=?";
		Object[] paras = { name };
		try {
			chargeType = findByProperties(sql, paras);
		} catch (Exception er) {
			throw er;
		}
		return chargeType;
	}
}