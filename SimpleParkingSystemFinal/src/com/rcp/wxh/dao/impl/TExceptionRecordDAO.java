package com.rcp.wxh.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rcp.wxh.dao.inter.BaseHibernateDAO;
import com.rcp.wxh.pojo.TExceptionRecord;

/**
 * A data access object (DAO) providing persistence and search support for TExceptionRecord entities.
 * Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be augmented to handle user-managed Spring
 * transactions.
 * Each of these methods provides additional information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.rcp.wxh.dao.impl.TExceptionRecord
 * @author MyEclipse Persistence Tools
 */

public class TExceptionRecordDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(TExceptionRecordDAO.class);
	// property constants
	public static final String ID = "id";
	public static final String EXCEPTION = "exception";
	public static final String REMARK = "remark";
	public static final String ENTITY = "TExceptionRecord";

	/**
	 * 查询时间段内异常信息记录
	 * 
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public List<Object> getExceptionsByPeriod(Date startTime, Date endTime) throws Exception {
		List<Object> exceptions = null;
		Object[] paras = { startTime, endTime };
		String sql = "from TExceptionRecord where exceptiontime between ? and ? order by exceptiontime desc";
		try {
			exceptions = findByProperties(sql, paras);
		} catch (Exception e) {
			throw e;
		}
		return exceptions;

	}

	public static void main(String args[]) throws Exception {
		TExceptionRecordDAO terd = new TExceptionRecordDAO();
		for (int i = 0; i < 300; i++) {
			TExceptionRecord ter = new TExceptionRecord();
			ter.setException("异常啦！" + i);
			ter.setExceptiontime(new Date());
			terd.save(ter);
		}
	}

}