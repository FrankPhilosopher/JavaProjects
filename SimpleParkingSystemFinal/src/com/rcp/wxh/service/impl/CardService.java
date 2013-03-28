package com.rcp.wxh.service.impl;

import java.util.List;

import com.rcp.wxh.dao.impl.TCardDao;
import com.rcp.wxh.pojo.TCard;

/**
 * 有关card 对象的业务逻辑处理
 * 
 * @author JH
 *         2011 11 3
 */
public class CardService extends ExceptionEvent {

	private final String module = "卡片管理模块";

	/**
	 * 获取所有卡片信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAllCards() throws Exception {
		List<Object> cards = null;
		TCardDao tdao = new TCardDao();
		try {
			cards = tdao.findAll(TCard.class);
		} catch (Exception e) {
			fireExceptionRecord("获取所有卡片信息异常!", module);
			e.printStackTrace();
			throw e;
		}
		return cards;
	}

	/**
	 * 新增卡
	 * 
	 * @param card
	 * @throws Exception
	 */
	public void addCard(TCard card) throws Exception {
		TCardDao tdao = new TCardDao();
		try {
			tdao.save(card);
		} catch (Exception e) {
			fireExceptionRecord("新注册卡片信息异常!卡号:" + card.getCardid(), module);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 删除卡
	 * 
	 * @param card
	 * @throws Exception
	 */
	public void delCard(Object card) throws Exception {
		TCardDao tdao = new TCardDao();
		try {
			tdao.delte(card);
		} catch (Exception e) {
			fireExceptionRecord("删除卡片信息异常!卡号:" + ((TCard) card).getCardid(), module);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 更新卡
	 * 
	 * @param card
	 * @throws Exception
	 */
	public void updateCard(TCard card) throws Exception {
		TCardDao tdao = new TCardDao();
		try {
			tdao.update(card);
		} catch (Exception e) {
			fireExceptionRecord("更新卡片信息异常!卡号:" + card.getCardid(), module);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 根据ID查询卡
	 * 
	 * @param card
	 * @throws Exception
	 */
	public TCard getCardById(String cardid) throws Exception {
		TCard card = null;
		TCardDao tdao = new TCardDao();
		try {
			card = (TCard) tdao.findById(TCard.class, cardid);
		} catch (Exception e) {
			fireExceptionRecord("查询卡片信息异常!卡号:" + cardid, module);
			e.printStackTrace();
			throw e;
		}
		return card;

	}

	/**
	 * 根据类型ID查询卡
	 * 
	 * @param card
	 * @throws Exception
	 */
	public List<Object> getCardByCardTypeId(int typeid) throws Exception {
		List<Object> card = null;
		TCardDao tdao = new TCardDao();
		try {
			card = tdao.getCardByPara(new Object[] { typeid }, TCardDao.CARDTYPEID);
		} catch (Exception e) {
			fireExceptionRecord("根据卡类型查询卡片信息异常!", module);
			e.printStackTrace();
			throw e;
		}
		return card;
	}

	/**
	 * 根据状态查询卡
	 * 
	 * @param card
	 * @throws Exception
	 */
	public List<Object> getCardByCardStatus(int status) throws Exception {
		List<Object> card = null;
		TCardDao tdao = new TCardDao();
		try {
			card = tdao.getCardByPara(new Object[] { status }, TCardDao.CARDSTATUS);
		} catch (Exception e) {
			throw e;
		}
		return card;
	}

	/**
	 * 根据车牌号码查询
	 * 
	 * @param carnumber
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public List<Object> getCardByCarnumber(String carnumber) throws Exception {
		List<Object> cards = null;
		TCardDao tdao = new TCardDao();
		try {
			cards = tdao.getCardByPara(new Object[] { "%" + carnumber + "%" }, TCardDao.CARNUMBER);
		} catch (Exception e) {
			fireExceptionRecord("根据车牌号码查询卡片信息异常!", module);
			e.printStackTrace();
			throw e;
		}
		return cards;
	}

	/**
	 * 根据车牌号码以及卡片类型查询
	 * 
	 * @param carnumber
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public List<Object> getCardByCarnumberAndType(String carnumber, int type) throws Exception {
		List<Object> cards = null;
		TCardDao tdao = new TCardDao();
		try {
			cards = tdao.getCardByPara(new Object[] { "%" + carnumber + "%", type }, TCardDao.CARNUMBGER_AND_TYPE);
		} catch (Exception e) {
			fireExceptionRecord("根据车牌号码和卡片类型查询卡片信息异常!", module);
			e.printStackTrace();
			throw e;
		}
		return cards;
	}

	/**
	 * 根据 卡片号 和类型来查询
	 * 
	 * @param carid
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<Object> getCardByCardidAndType(String carid, int type) throws Exception {
		List<Object> cards = null;
		TCardDao tdao = new TCardDao();
		try {
			cards = tdao.getCardByPara(new Object[] { carid, type }, TCardDao.CARDID_AND_TYPE);
		} catch (Exception e) {
			fireExceptionRecord("根据卡号和卡片类型查询卡片信息异常!", module);
			e.printStackTrace();
			throw e;
		}
		return cards;
	}

	/**
	 * 根据车主姓名查询卡信息
	 * 
	 * @param ownerName
	 * @return
	 * @throws Exception
	 */
	public List<Object> getCardByOwnerName(String ownerName) throws Exception {
		List<Object> cards = null;
		TCardDao tdao = new TCardDao();
		try {
			cards = tdao.getCardByPara(new Object[] { ownerName }, TCardDao.OWNERNAME);
		} catch (Exception e) {
			fireExceptionRecord("根据车主姓名查询卡片信息异常!", module);
			e.printStackTrace();
			throw e;
		}
		return cards;
	}

	/**
	 * 根据车主姓名 以及卡片类型查询卡信息
	 * 
	 * @param ownerName
	 * @return
	 * @throws Exception
	 */
	public List<Object> getCardByOwnerNameAndType(String ownerName, int type) throws Exception {
		List<Object> cards = null;
		TCardDao tdao = new TCardDao();
		try {
			cards = tdao.getCardByPara(new Object[] { ownerName, type }, TCardDao.OWNERNAME_AND_TYPE);
		} catch (Exception e) {
			fireExceptionRecord("根据车主姓名和卡片类型查询卡片信息异常!", module);
			e.printStackTrace();
			throw e;
		}
		return cards;
	}

}
