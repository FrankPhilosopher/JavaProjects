package com.rcp.wxh.dao.impl;

import java.util.List;

import com.rcp.wxh.dao.inter.BaseHibernateDAO;

public class TCardTypeDao extends BaseHibernateDAO {

	/**
	 * 根据卡片名查询卡类型
	 * 
	 * @param cardTypeId
	 * @throws Exception
	 */
	public List<Object> getCardTypeByName(String name) throws Exception {
		List<Object> cardType = null;
		String sql = "from TCardType where name=?";
		Object[] paras = { name };
		try {
			cardType = findByProperties(sql, paras);
		} catch (Exception er) {
			throw er;
		}
		return cardType;
	}

	/**
	 * 根据收费类型编号ID查询卡类型
	 * 
	 * @param cardTypeId
	 * @throws Exception
	 */
	public List<Object> getCardTypeByChargeTypeId(int chargeTypeId) throws Exception {
		List<Object> cardType = null;
		String sql = "from TCardType where chargetypeid=?";
		Object[] paras = { chargeTypeId };
		try {
			cardType = findByProperties(sql, paras);
		} catch (Exception er) {
			throw er;
		}
		return cardType;
	}
}
