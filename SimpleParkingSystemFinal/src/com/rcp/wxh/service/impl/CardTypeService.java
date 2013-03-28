package com.rcp.wxh.service.impl;

import java.util.List;

import com.rcp.wxh.dao.impl.TCardTypeDao;
import com.rcp.wxh.pojo.TCardType;

public class CardTypeService extends ExceptionEvent {

	private final String module = "卡片类型管理模块";

	/**
	 * 新增卡类型
	 * 
	 * @param cardType
	 * @throws Exception
	 */
	public void addCardType(TCardType cardType) throws Exception {
		TCardTypeDao tdao = new TCardTypeDao();
		try {
			tdao.save(cardType);
		} catch (Exception e) {
			fireExceptionRecord("新增卡片类型信息异常!", module);
			throw e;
		}
	}

	/**
	 * 删除卡类型
	 * 
	 * @param cardType
	 * @throws Exception
	 */
	public void delCardType(TCardType cardType) throws Exception {
		TCardTypeDao tdao = new TCardTypeDao();
		try {
			tdao.delte(cardType);
		} catch (Exception e) {
			fireExceptionRecord("删除卡片类型信息异常!", module);
			throw e;
		}
	}

	/**
	 * 更新卡类型
	 * 
	 * @param cardType
	 * @throws Exception
	 */
	public void updateCardType(TCardType cardType) throws Exception {
		TCardTypeDao tdao = new TCardTypeDao();
		try {
			tdao.update(cardType);
		} catch (Exception e) {
			fireExceptionRecord("更新卡片类型信息异常!", module);
			throw e;
		}
	}

	/**
	 * 根据卡类型ID查询卡类型
	 * 
	 * @param cardTypeId
	 * @throws Exception
	 */
	public TCardType getCardTypeById(int cardTypeId) throws Exception {
		TCardTypeDao tdao = new TCardTypeDao();
		TCardType cardType = null;
		try {
			cardType = (TCardType) tdao.findById(TCardType.class, cardTypeId);
		} catch (Exception e) {
			throw e;
		}
		return cardType;
	}

	/**
	 * 根据卡片名查询卡类型
	 * 
	 * @param cardTypeId
	 * @throws Exception
	 */
	public List<Object> getCardTypeByName(String name) throws Exception {
		TCardTypeDao tdao = new TCardTypeDao();
		List<Object> cardType = null;
		try {
			cardType = tdao.getCardTypeByName(name);
		} catch (Exception e) {
			throw e;
		}
		return cardType;
	}

	/**
	 * 根据卡片名查询卡类型
	 * 
	 * @param cardTypeId
	 * @throws Exception
	 */
	public List<Object> getCardTypeByChargeTypeId(int chargeTypeId) throws Exception {
		TCardTypeDao tdao = new TCardTypeDao();
		List<Object> cardType = null;
		try {
			cardType = tdao.getCardTypeByChargeTypeId(chargeTypeId);
		} catch (Exception e) {
			throw e;
		}
		return cardType;
	}

	/**
	 * 获取所有卡片类型
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAllCardTypes() throws Exception {
		TCardTypeDao tdao = new TCardTypeDao();
		List<Object> cardTypes = null;
		try {
			cardTypes = tdao.findAll(TCardType.class);
		} catch (Exception e) {
			fireExceptionRecord("获取所有卡片类型信息异常!", module);
			throw e;
		}
		return cardTypes;
	}

}
