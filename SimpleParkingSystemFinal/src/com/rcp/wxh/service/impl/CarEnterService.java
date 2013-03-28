package com.rcp.wxh.service.impl;

import java.util.List;

import com.rcp.wxh.dao.impl.TCarEnterDao;
import com.rcp.wxh.pojo.TCarEnter;

/**
 * 在场车辆 业务逻辑层操作
 * 
 * @author wuxuehong 2011-11-25
 * 
 */
public class CarEnterService extends ExceptionEvent {

	private final String module = "入场车辆记录管理";

	/**
	 * 车辆入场 新增在场车辆记录
	 * 
	 * @throws Exception
	 */
	public void addCarEnter(TCarEnter tce) throws Exception {
		TCarEnterDao tedao = new TCarEnterDao();
		try {
			tedao.saveOrUpdate(tce);
		} catch (Exception e) {
			fireExceptionRecord("入场车辆记录信息新增异常", module);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 删除车辆在场记录
	 * 
	 * @param tce
	 * @throws Exception
	 */
	public void delCarEnter(TCarEnter tce) throws Exception {
		TCarEnterDao tedao = new TCarEnterDao();
		try {
			tedao.delte(tce);
		} catch (Exception e) {
			fireExceptionRecord("入场车辆记录信息删除异常", module);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 获取在场车辆总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public int getCarEnterNum() throws Exception {
		int total = 0;
		TCarEnterDao tedao = new TCarEnterDao();
		try {
			total = tedao.getTotalNum();
		} catch (Exception e) {
			fireExceptionRecord("获取在场车辆数信息异常", module);
			throw e;
		}
		return total;
	}

	/**
	 * 根据卡号 获取车辆入场记录
	 * 
	 * @param cardid
	 * @return
	 * @throws Exception
	 */
	public TCarEnter getCarEnterByCardid(String cardid) throws Exception {
		TCarEnter tce = null;
		TCarEnterDao tedao = new TCarEnterDao();
		try {
			tce = tedao.getCarEnterByCardid(cardid);
		} catch (Exception e) {
			fireExceptionRecord("获取入场信息异常", module);
			throw e;
		}
		return tce;
	}

	/**
	 * 获取当前停车场内车辆信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Object> getCarEnters() throws Exception {
		List<Object> list = null;
		TCarEnterDao tedao = new TCarEnterDao();
		try {
			list = tedao.findAll(TCarEnter.class);
		} catch (Exception e) {
			fireExceptionRecord("获取入场信息异常", module);
			throw e;
		}
		return list;
	}

}
