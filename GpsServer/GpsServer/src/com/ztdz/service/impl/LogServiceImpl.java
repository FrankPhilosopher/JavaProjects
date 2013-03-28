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

	// ����һ����־��Ϣ
	public void addLog(TLog tLog) {
		tLogDAO.save(tLog);
	}

	// ������־����ɾ����־��Ϣ
	public void deleteLog(Integer logId) {
		tLogDAO.delete(tLogDAO.findById(logId));
	}

	// �޸���־��Ϣ
	public void updateLog(TLog tLog) {
		tLogDAO.attachDirty(tLog);
	}

	// ������־������ѯ��־��Ϣ
	public TLog findById(Integer logId) {
		return tLogDAO.findById(logId);
	}

	// ��ѯ������־��Ϣ(����ҳ)
	public List<TLog> findAll() {
		return tLogDAO.findAll(-1, -1);
	}

	// ��ѯ������־��Ϣ(��ҳ)
	public List<TLog> findAll(int firstResult, int maxResults) {
		return tLogDAO.findAll(firstResult, maxResults);
	}

	// ��ѯ������־��Ϣ������
	public int getCountAll() {
		return tLogDAO.getCountAll();
	}

	// ������־��userid(����Ա�˺�)��ѯ��־��Ϣ (����ҳ) firstResult�Ǵ�0��ʼ��
	public List<TLog> findByUserId(String userId) {
		return tLogDAO.findByUserId(userId, -1, -1);
	}

	// ������־��userid(����Ա�˺�)��ѯ��־��Ϣ (��ҳ) firstResult�Ǵ�0��ʼ��
	public List<TLog> findByUserId(String userId, int firstResult,
			int maxResults) {
		return tLogDAO.findByUserId(userId, firstResult, maxResults);
	}

	// ������־��userid(����Ա�˺�)��ѯ��־��Ϣ������ firstResult�Ǵ�0��ʼ��
	public int getCountByUserId(String userId) {
		return tLogDAO.getCountByUserId(userId);
	}

	// ������־��ʱ�������ѯ��־��Ϣ (����ҳ) firstResult�Ǵ�0��ʼ��
	public List<TLog> findByTimeBetween(Date timeStart, Date timeEnd) {
		return tLogDAO.findByTimeBetween(timeStart, timeEnd, -1, -1);
	}

	// ������־��ʱ�������ѯ��־��Ϣ (��ҳ) firstResult�Ǵ�0��ʼ��
	public List<TLog> findByTimeBetween(Date timeStart, Date timeEnd,
			int firstResult, int maxResults) {
		return tLogDAO.findByTimeBetween(timeStart, timeEnd, firstResult,
				maxResults);
	}

	// ������־��ʱ�������ѯ��������־��Ϣ������ firstResult�Ǵ�0��ʼ��
	public int getCountByTimeBetween(Date timeStart, Date timeEnd) {
		return tLogDAO.getCountByTimeBetween(timeStart, timeEnd);
	}

	// ������־��userId��ʱ�������ѯ ��־��Ϣ (����ҳ) firstResult�Ǵ�0��ʼ��
	public List<TLog> findByUserIdByTimeBetween(String userId, Date timeStart,
			Date timeEnd) {
		return tLogDAO.findByUserIdByTimeBetween(userId, timeStart, timeEnd,
				-1, -1);
	}

	// ������־��userId��ʱ�������ѯ ��־��Ϣ (��ҳ) firstResult�Ǵ�0��ʼ��
	public List<TLog> findByUserIdByTimeBetween(String userId, Date timeStart,
			Date timeEnd, int firstResult, int maxResults) {
		return tLogDAO.findByUserIdByTimeBetween(userId, timeStart, timeEnd,
				firstResult, maxResults);
	}

	// ������־��userId��ʱ�������ѯ ��־��Ϣ ������ firstResult�Ǵ�0��ʼ��
	public int getCountByUserIdByTimeBetween(String userId, Date timeStart,
			Date timeEnd) {
		return tLogDAO
				.getCountByUserIdByTimeBetween(userId, timeStart, timeEnd);
	}
}
