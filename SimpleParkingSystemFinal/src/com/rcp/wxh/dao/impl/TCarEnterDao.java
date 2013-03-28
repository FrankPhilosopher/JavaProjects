package com.rcp.wxh.dao.impl;

import com.rcp.wxh.dao.inter.BaseHibernateDAO;
import com.rcp.wxh.pojo.TCarEnter;

/**
 * 在场车辆 数据库层操作
 * 
 * @author wuxuehong 2011-11-25
 * 
 */
public class TCarEnterDao extends BaseHibernateDAO {

	/**
	 * 获取在场车辆数
	 * 
	 * @return
	 * @throws Exception
	 */
	public int getTotalNum() throws Exception {
		int total = 0;
		String sql = "select count(1) from t_car_enter";
		try {
			Object o = getCount(sql);
			total = (Integer) o;
		} catch (Exception e) {
			throw e;
		}
		return total;
	}

	/**
	 * 根据卡号 获取 车辆入场记录
	 * 
	 * @param cardID
	 * @return
	 * @throws Exception
	 */
	public TCarEnter getCarEnterByCardid(String cardID) throws Exception {
		TCarEnter tce = null;
		String sql = "from TCarEnter where cardid=?";
		try {
			tce = (TCarEnter) findByParas(sql, new Object[] { cardID });
		} catch (Exception e) {
			throw e;
		}
		return tce;
	}
}
