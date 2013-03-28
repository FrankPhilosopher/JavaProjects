package com.rcp.wxh.service.impl;

import java.util.Date;
import java.util.List;

import com.rcp.wxh.dao.impl.TCarRecordDao;
import com.rcp.wxh.pojo.TCarRecord;

public class CarRecordService extends ExceptionEvent {

	private final String module = "车辆出入管理";

	/**
	 * 新增车辆记录
	 * 
	 * @param carRecord
	 * @throws Exception
	 */
	public void addCarRecord(TCarRecord carRecord) throws Exception {
		TCarRecordDao tdao = new TCarRecordDao();
		try {
			tdao.save(carRecord);
		} catch (Exception e) {
			fireExceptionRecord("新增停车记录异常!", module);
			throw e;
		}
	}

	/**
	 * 删除车辆记录
	 * 
	 * @param carRecord
	 * @throws Exception
	 */
	public void delCarRecord(TCarRecord carRecord) throws Exception {
		TCarRecordDao tdao = new TCarRecordDao();
		try {
			tdao.delte(carRecord);
		} catch (Exception e) {
			fireExceptionRecord("删除停车记录异常!", module);
			throw e;
		}
	}

	/**
	 * 更新车辆记录
	 * 
	 * @param carRecord
	 * @throws Exception
	 */
	public void updateCarRecord(TCarRecord carRecord) throws Exception {
		TCarRecordDao tdao = new TCarRecordDao();
		try {
			tdao.update(carRecord);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 根据ID查询车辆记录
	 * 
	 * @param carRecordId
	 * @throws Exception
	 */
	public TCarRecord getCarRecordById(int carRecordId) throws Exception {
		TCarRecord carRecord = null;
		TCarRecordDao tdao = new TCarRecordDao();
		try {
			carRecord = (TCarRecord) tdao.findById(TCarRecord.class, carRecordId);
		} catch (Exception e) {
			throw e;
		}
		return carRecord;
	}

	/**
	 * 查询时间段内所有车辆出入记录
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAllCarRecords(Date start, Date end) throws Exception {
		List<Object> carRecords = null;
		TCarRecordDao tdao = new TCarRecordDao();
		try {
			carRecords = tdao.getCarRecordsByParas(new Object[] { start, end }, TCarRecordDao.ALL);
		} catch (Exception e) {
			throw e;
		}
		return carRecords;
	}

	/**
	 * 根据卡号 查询时间段内所有车辆出入记录
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List<Object> getCarRecordsByCardID(Date start, Date end, String cardID) throws Exception {
		List<Object> carRecords = null;
		TCarRecordDao tdao = new TCarRecordDao();
		try {
			carRecords = tdao.getCarRecordsByParas(new Object[] { start, end, cardID }, TCarRecordDao.CARDID);
		} catch (Exception e) {
			throw e;
		}
		return carRecords;
	}

	/**
	 * 根据车牌号码 查询时间段内所有车辆出入记录
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List<Object> getCarRecordsByCarnumber(Date start, Date end, String carnumber) throws Exception {
		List<Object> carRecords = null;
		TCarRecordDao tdao = new TCarRecordDao();
		try {
			carRecords = tdao.getCarRecordsByParas(new Object[] { "%" + carnumber + "%", start, end },
					TCarRecordDao.CARNUMBER);
		} catch (Exception e) {
			throw e;
		}
		return carRecords;
	}

	/**
	 * 根据车主姓名 查询时间段内所有车辆出入记录
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 */
	public List<Object> getCarRecordsByOwnername(Date start, Date end, String ownername) throws Exception {
		List<Object> carRecords = null;
		TCarRecordDao tdao = new TCarRecordDao();
		try {
			carRecords = tdao.getCarRecordsByParas(new Object[] { "%" + ownername + "%", start, end },
					TCarRecordDao.OWNERNAME);
		} catch (Exception e) {
			throw e;
		}
		return carRecords;
	}

}
