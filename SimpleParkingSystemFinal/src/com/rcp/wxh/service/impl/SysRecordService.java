package com.rcp.wxh.service.impl;

import com.rcp.wxh.dao.impl.TSysRecordDao;
import com.rcp.wxh.pojo.TSysRecord;

public class SysRecordService {
	/**
	 * 新增系统操作记录
	 * 
	 * @param sysRecord
	 * @throws Exception
	 */
	public void addSysRecord(TSysRecord sysRecord) throws Exception {
		TSysRecordDao tdao = new TSysRecordDao();
		try {
			tdao.save(sysRecord);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 删除系统操作记录
	 * 
	 * @param sysRecord
	 * @throws Exception
	 */
	public void delSysRecord(TSysRecord sysRecord) throws Exception {
		TSysRecordDao tdao = new TSysRecordDao();
		try {
			tdao.delte(sysRecord);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 更新系统操作记录
	 * 
	 * @param sysRecord
	 * @throws Exception
	 */
	public void updateSysRecord(TSysRecord sysRecord) throws Exception {
		TSysRecordDao tdao = new TSysRecordDao();
		try {
			tdao.update(sysRecord);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 根据id查询系统操作记录
	 * 
	 * @param id
	 * @throws Exception
	 */
	public TSysRecord getSysRecordById(int sysRecordId) throws Exception {
		TSysRecordDao tdao = new TSysRecordDao();
		TSysRecord sysRecord = null;
		try {
			sysRecord = (TSysRecord) tdao.findById(TSysRecord.class, sysRecordId);
		} catch (Exception e) {
			throw e;
		}
		return sysRecord;
	}

}
