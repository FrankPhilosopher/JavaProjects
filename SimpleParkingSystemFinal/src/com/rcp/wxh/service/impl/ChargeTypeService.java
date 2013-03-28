package com.rcp.wxh.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.rcp.wxh.dao.impl.TChargeTypeDao;
import com.rcp.wxh.pojo.TChargeType;

/**
 * 收费方式业务逻辑处理
 * 
 * @author wuxuehong 2011-11-9
 * 
 */
public class ChargeTypeService extends ExceptionEvent {

	private final String module = "收费方式管理";

	/**
	 * 新增收费方式
	 * 
	 * @param chargeType
	 * @throws Exception
	 */
	public void addChargeType(TChargeType chargeType) throws Exception {
		TChargeTypeDao tdao = new TChargeTypeDao();
		try {
			tdao.save(chargeType);
		} catch (Exception e) {
			fireExceptionRecord("新增收费方式异常!", module);
			throw e;
		}
	}

	/**
	 * 删除收费方式
	 * 
	 * @param chargeType
	 * @throws Exception
	 */
	public void delChargeType(TChargeType chargeType) throws Exception {
		TChargeTypeDao tdao = new TChargeTypeDao();
		try {
			tdao.delte(chargeType);
		} catch (Exception e) {
			fireExceptionRecord("删除收费方式异常!", module);
			throw e;
		}
	}

	/**
	 * 更新收费方式
	 * 
	 * @param chargeType
	 * @throws Exception
	 */
	public void updateChargeType(TChargeType chargeType) throws Exception {
		TChargeTypeDao tdao = new TChargeTypeDao();
		try {
			tdao.update(chargeType);
		} catch (Exception e) {
			fireExceptionRecord("更新收费方式异常!", module);
			throw e;
		}
	}

	/**
	 * 根据收费类型编号查询收费类型
	 * 
	 * @param chargeTypeId
	 * @throws Exception
	 */
	public TChargeType getChargeTypeById(int chargeTypeId) throws Exception {
		TChargeTypeDao tdao = new TChargeTypeDao();
		TChargeType chargeType = null;
		try {
			chargeType = (TChargeType) tdao.findById(TChargeType.class, chargeTypeId);
		} catch (Exception e) {
			throw e;
		}
		return chargeType;
	}

	/**
	 * 获取所有的收费方式
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAllChargeTypes() throws Exception {
		TChargeTypeDao tdao = new TChargeTypeDao();
		List<Object> chargeTypes;
		try {
			chargeTypes = tdao.findAll(TChargeType.class);
		} catch (Exception e) {
			fireExceptionRecord("查询所有收费方式异常!", module);
			throw e;
		}
		return chargeTypes;
	}

	/**
	 * 根据收费方式名称查询收费方式
	 * 
	 * @param name
	 * @throws Exception
	 */
	public List<Object> getChargeTypeByName(String name) throws Exception {
		List<Object> chargeType = null;
		TChargeTypeDao tdao = new TChargeTypeDao();
		try {
			chargeType = tdao.getChargeTypeByName(name);
		} catch (Exception er) {
			throw er;
		}
		return chargeType;
	}

	/**
	 * test!!!!!!!!!!!!
	 * 
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String args[]) throws UnsupportedEncodingException {
		ChargeTypeService cts = new ChargeTypeService();
		TChargeType tct = new TChargeType();
		tct.setName(new String("你好".getBytes(), "GB2312"));
		tct.setType(5);
		try {
			cts.addChargeType(tct);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
