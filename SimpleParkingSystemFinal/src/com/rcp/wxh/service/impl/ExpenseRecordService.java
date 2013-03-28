package com.rcp.wxh.service.impl;

import java.util.Date;
import java.util.List;

import com.rcp.wxh.dao.impl.TExpenseRecordDao;
import com.rcp.wxh.pojo.TExpenseRecord;

public class ExpenseRecordService {
	/**
	 * 新增收费记录
	 * 
	 * @param expRecord
	 * @throws Exception
	 */
	public void addExpenseRecord(TExpenseRecord expRecord) throws Exception {
		TExpenseRecordDao tdao = new TExpenseRecordDao();
		try {
			tdao.save(expRecord);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 删除收费记录
	 * 
	 * @param expRecord
	 * @throws Exception
	 */
	public void delExpenseRecord(TExpenseRecord expRecord) throws Exception {
		TExpenseRecordDao tdao = new TExpenseRecordDao();
		try {
			tdao.delte(expRecord);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 更新收费记录
	 * 
	 * @param expRecord
	 * @throws Exception
	 */
	public void updateExpenseRecord(TExpenseRecord expRecord) throws Exception {
		TExpenseRecordDao tdao = new TExpenseRecordDao();
		try {
			tdao.update(expRecord);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 根据id查询收费记录
	 * 
	 * @param id
	 * @throws Exception
	 */
	public TExpenseRecord getExpenseRecordById(int expRecordId) throws Exception {
		TExpenseRecordDao tdao = new TExpenseRecordDao();
		TExpenseRecord expRecord = null;
		try {
			expRecord = (TExpenseRecord) tdao.findById(TExpenseRecord.class, expRecordId);
		} catch (Exception e) {
			throw e;
		}
		return expRecord;
	}

	/**
	 * 根据卡号查询收费记录
	 * 
	 * @param cardId
	 * @throws Exception
	 */
	public List<Object> getExpRecordByCardId(String cardId, Date start, Date end) throws Exception {
		List<Object> expRecord = null;
		TExpenseRecordDao tdao = new TExpenseRecordDao();
		try {
			expRecord = tdao.getExpRecordByParas(new Object[] { cardId, start, end }, TExpenseRecordDao.CARDID);
		} catch (Exception er) {
			throw er;
		}
		return expRecord;
	}

	/**
	 * 根据车牌号码查询收费记录
	 * 
	 * @param cardId
	 * @throws Exception
	 */
	public List<Object> getExpRecordByCarnumber(String carnumber, Date start, Date end) throws Exception {
		List<Object> expRecord = null;
		TExpenseRecordDao tdao = new TExpenseRecordDao();
		try {
			expRecord = tdao.getExpRecordByParas(new Object[] { "%" + carnumber + "%", start, end },
					TExpenseRecordDao.CARNUMBER);
		} catch (Exception er) {
			throw er;
		}
		return expRecord;
	}

	/**
	 * 根据车主姓名查询收费记录
	 * 
	 * @param cardId
	 * @throws Exception
	 */
	public List<Object> getExpRecordByOwnername(String ownername, Date start, Date end) throws Exception {
		List<Object> expRecord = null;
		TExpenseRecordDao tdao = new TExpenseRecordDao();
		try {
			expRecord = tdao.getExpRecordByParas(new Object[] { "%" + ownername + "%", start, end },
					TExpenseRecordDao.OWNERNAME);
		} catch (Exception er) {
			throw er;
		}
		return expRecord;
	}

	/**
	 * 获取所有收费记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAllExpRecords(Date start, Date end) throws Exception {
		List<Object> expRecord = null;
		TExpenseRecordDao tdao = new TExpenseRecordDao();
		try {
			expRecord = tdao.getExpRecordByParas(new Object[] { start, end }, TExpenseRecordDao.ALL);
		} catch (Exception er) {
			throw er;
		}
		return expRecord;
	}
}
