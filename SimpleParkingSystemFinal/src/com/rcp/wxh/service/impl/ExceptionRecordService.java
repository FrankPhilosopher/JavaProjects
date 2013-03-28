package com.rcp.wxh.service.impl;

import java.util.Date;
import java.util.List;

import com.rcp.wxh.dao.impl.TExceptionRecordDAO;
import com.rcp.wxh.pojo.TExceptionRecord;

public class ExceptionRecordService {
	/**
	 * 新增异常信息记录
	 * 
	 * @param ecpRecord
	 * @throws Exception
	 */
	public void addExceptionRecord(TExceptionRecord ecpRecord) throws Exception {
		TExceptionRecordDAO tdao = new TExceptionRecordDAO();
		try {
			tdao.save(ecpRecord);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 删除异常信息记录
	 * 
	 * @param ecpRecord
	 * @throws Exception
	 */
	public void delExceptionRecord(TExceptionRecord ecpRecord) throws Exception {
		TExceptionRecordDAO tdao = new TExceptionRecordDAO();
		try {
			tdao.delte(ecpRecord);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 更新异常信息记录
	 * 
	 * @param ecpRecord
	 * @throws Exception
	 */
	public void updateExceptionRecord(TExceptionRecord ecpRecord) throws Exception {
		TExceptionRecordDAO tdao = new TExceptionRecordDAO();
		try {
			tdao.update(ecpRecord);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 根据异常信息ID查询异常信息记录
	 * 
	 * @param ecpRecordId
	 * @throws Exception
	 */
	public TExceptionRecord getEcpRecordById(int ecpRecordId) throws Exception {
		TExceptionRecordDAO tdao = new TExceptionRecordDAO();
		TExceptionRecord ecpRecord = null;
		try {
			ecpRecord = (TExceptionRecord) tdao.findById(TExceptionRecord.class, ecpRecordId);
		} catch (Exception e) {
			throw e;
		}
		return ecpRecord;
	}

	/**
	 * 根据时间段查询异常信息记录
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public List<Object> getEcpRecordByPeriod(Date startTime, Date endTime) throws Exception {
		TExceptionRecordDAO tdao = new TExceptionRecordDAO();
		List<Object> exceptions = null;
		try {
			exceptions = tdao.getExceptionsByPeriod(startTime, endTime);
		} catch (Exception e) {
			throw e;
		}
		return exceptions;
	}
}
