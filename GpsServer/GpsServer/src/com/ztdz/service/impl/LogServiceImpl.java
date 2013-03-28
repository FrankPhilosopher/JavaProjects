package com.ztdz.service.impl;

import java.util.Date;
import java.util.List;

import com.ztdz.dao.impl.TLogDAO;
import com.ztdz.pojo.TLog;

public class LogServiceImpl {
	private TLogDAO tLogDAO;

	public LogServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public void settLogDAO(TLogDAO tLogDAO) {
		this.tLogDAO = tLogDAO;
	}

	public TLogDAO gettLogDAO() {
		return tLogDAO;
	}

	// 增加一个日志信息
	public void addLog(TLog tLog) {
		tLogDAO.save(tLog);
	}

	// 根据日志主键删除日志信息
	public void deleteLog(Integer logId) {
		tLogDAO.delete(tLogDAO.findById(logId));
	}

	// 修改日志信息
	public void updateLog(TLog tLog) {
		tLogDAO.attachDirty(tLog);
	}

	// 根据日志主键查询日志信息
	public TLog findById(Integer logId) {
		return tLogDAO.findById(logId);
	}

	// 查询所有日志信息(不分页)
	public List<TLog> findAll() {
		return tLogDAO.findAll(-1, -1);
	}

	// 查询所有日志信息(分页)
	public List<TLog> findAll(int firstResult, int maxResults) {
		return tLogDAO.findAll(firstResult, maxResults);
	}

	// 查询所有日志信息的总数
	public int getCountAll() {
		return tLogDAO.getCountAll();
	}

	// 根据日志的userid(操作员账号)查询日志信息 (不分页) firstResult是从0开始的
	public List<TLog> findByUserId(String userId) {
		return tLogDAO.findByUserId(userId, -1, -1);
	}

	// 根据日志的userid(操作员账号)查询日志信息 (分页) firstResult是从0开始的
	public List<TLog> findByUserId(String userId, int firstResult,
			int maxResults) {
		return tLogDAO.findByUserId(userId, firstResult, maxResults);
	}

	// 根据日志的userid(操作员账号)查询日志信息的总数 firstResult是从0开始的
	public int getCountByUserId(String userId) {
		return tLogDAO.getCountByUserId(userId);
	}

	// 根据日志的时间区间查询日志信息 (不分页) firstResult是从0开始的
	public List<TLog> findByTimeBetween(Date timeStart, Date timeEnd) {
		return tLogDAO.findByTimeBetween(timeStart, timeEnd, -1, -1);
	}

	// 根据日志的时间区间查询日志信息 (分页) firstResult是从0开始的
	public List<TLog> findByTimeBetween(Date timeStart, Date timeEnd,
			int firstResult, int maxResults) {
		return tLogDAO.findByTimeBetween(timeStart, timeEnd, firstResult,
				maxResults);
	}

	// 根据日志的时间区间查询区间内日志信息的总数 firstResult是从0开始的
	public int getCountByTimeBetween(Date timeStart, Date timeEnd) {
		return tLogDAO.getCountByTimeBetween(timeStart, timeEnd);
	}

	// 根据日志的userId和时间区间查询 日志信息 (不分页) firstResult是从0开始的
	public List<TLog> findByUserIdByTimeBetween(String userId, Date timeStart,
			Date timeEnd) {
		return tLogDAO.findByUserIdByTimeBetween(userId, timeStart, timeEnd,
				-1, -1);
	}

	// 根据日志的userId和时间区间查询 日志信息 (分页) firstResult是从0开始的
	public List<TLog> findByUserIdByTimeBetween(String userId, Date timeStart,
			Date timeEnd, int firstResult, int maxResults) {
		return tLogDAO.findByUserIdByTimeBetween(userId, timeStart, timeEnd,
				firstResult, maxResults);
	}

	// 根据日志的userId和时间区间查询 日志信息 的总数 firstResult是从0开始的
	public int getCountByUserIdByTimeBetween(String userId, Date timeStart,
			Date timeEnd) {
		return tLogDAO
				.getCountByUserIdByTimeBetween(userId, timeStart, timeEnd);
	}
}
