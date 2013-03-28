package com.ztdz.service.impl;

import java.util.Date;
import java.util.List;

import com.ztdz.dao.impl.TAccountDAO;
import com.ztdz.pojo.TAccount;

public class AccountServiceImpl {
	private TAccountDAO accountDAO;

	public AccountServiceImpl() {
	}

	public void setAccountDAO(TAccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public TAccountDAO getAccountDAO() {
		return accountDAO;
	}

	// ����һ���˵�
	public void addAccount(TAccount account) {
		accountDAO.save(account);
	}

	// ������ɾ��һ���˵�
	public void deleteAccount(Integer id) {
		accountDAO.delete(accountDAO.findById(id));
	}

	// �޸�һ���˵�
	public void updateAccount(TAccount account) {
		accountDAO.attachDirty(account);
	}

	// ����������һ���˵�
	public TAccount findById(int id) {
		return accountDAO.findById(id);
	}

	// ���������˵� firstResult�Ǵ�0��ʼ��(����ҳ)
	public List findAll() {
		return accountDAO.findAll(-1, -1);
	}

	// ���������˵� firstResult�Ǵ�0��ʼ��(��ҳ)
	public List findAll(int firstResult, int maxResults) {
		return accountDAO.findAll(firstResult, maxResults);
	}

	// ���������˵�������
	public int getCountAll() {
		return accountDAO.getCountAll();
	}

	// ��������������ѯ �丶�˵������˵�(��ҳ)
	public List findByPaider(String paiderName, int firstResult, int maxResults) {
		return accountDAO.findByPaider(paiderName, firstResult, maxResults);
	}

	// ������ID��ѯ���µĵ������˵�(��ҳ)
	public List findByOrgId(int orgId, int firstResult, int maxResults) {
		return accountDAO.findByOrgId(orgId, firstResult, maxResults);
	}

	// ��ѯһ��ʱ�������е��˵���Ϣ(��ҳ) firstResult�Ǵ�0��ʼ��
	public List findAllByTime(Date timeStart, Date timeEnd, int firstResult,
			int maxResults) {
		return accountDAO.findAllByTime(timeStart, timeEnd, firstResult,
				maxResults);
	}

	// ��ѯһ��ʱ����ĳһ����ID�µ������˵�(��ҳ)
	public List findByOrgIdByTime(int orgId, Date timeStart, Date timeEnd,
			int firstResult, int maxResults) {
		return accountDAO.findByOrgIdByTime(orgId, timeStart, timeEnd,
				firstResult, maxResults);
	}

	// ��ѯһ��ʱ����ĳһ������ �����˵������˵�(��ҳ)
	public List findByPaiderByTime(String paider, Date timeStart, Date timeEnd,
			int firstResult, int maxResults) {
		return accountDAO.findByPaiderByTime(paider, timeStart, timeEnd,
				firstResult, maxResults);
	}

	// ��������������ѯ �丶�˵������˵�(����ҳ)
	public List findByPaider(String paiderName) {
		return accountDAO.findByPaider(paiderName, -1, -1);
	}

	// ������ID��ѯ���µĵ������˵�(����ҳ)
	public List findByOrgId(int orgId) {
		return accountDAO.findByOrgId(orgId, -1, -1);
	}

	// ��ѯһ��ʱ�������е��˵���Ϣ(����ҳ) firstResult�Ǵ�0��ʼ��
	public List findAllByTime(Date timeStart, Date timeEnd) {
		return accountDAO.findAllByTime(timeStart, timeEnd, -1, -1);
	}

	// ��ѯһ��ʱ����ĳһ����ID�µ������˵�(����ҳ)
	public List findByOrgIdByTime(int orgId, Date timeStart, Date timeEnd) {
		return accountDAO.findByOrgIdByTime(orgId, timeStart, timeEnd, -1, -1);
	}

	// ��ѯһ��ʱ����ĳһ������ �����˵������˵�(����ҳ)
	public List findByPaiderByTime(String paider, Date timeStart, Date timeEnd) {
		return accountDAO
				.findByPaiderByTime(paider, timeStart, timeEnd, -1, -1);
	}

	// ��������������ѯ �丶�˵������˵�������
	public int getCountByPaider(String paiderName) {
		return accountDAO.getCountByPaider(paiderName);
	}

	// ������ID��ѯ���µĵ������˵�������
	public int getCountByOrgId(int orgId) {
		return accountDAO.getCountByOrgId(orgId);
	}

	// ��ѯһ��ʱ�������е��˵���Ϣ������ firstResult�Ǵ�0��ʼ��
	public int getCountAllByTime(Date timeStart, Date timeEnd) {
		return accountDAO.getCountAllByTime(timeStart, timeEnd);
	}

	// ��ѯһ��ʱ����ĳһ����ID�µ������˵�������
	public int getCountByOrgIdByTime(int orgId, Date timeStart, Date timeEnd) {
		return accountDAO.getCountByOrgIdByTime(orgId, timeStart, timeEnd);
	}

	// ��ѯһ��ʱ����ĳһ������ �����˵������˵�������
	public int getCountByPaiderByTime(String paider, Date timeStart,
			Date timeEnd) {
		return accountDAO.getCountByPaiderByTime(paider, timeStart, timeEnd);
	}

	// ��ѯһ��ʱ��Ϊĳһ����Ļ�����ֵ���缯�ţ��������˵�����ҳ��
	public List findByOrgLevel(int orgLevel, Date timeStart, Date timeEnd,
			int firstResult, int maxResults) {
		return accountDAO.findByOrgLevel(orgLevel, timeStart, timeEnd,
				firstResult, maxResults);
	}

	// ��ѯһ��ʱ��Ϊĳһ����Ļ�����ֵ���缯�ţ��������˵�������ҳ��

	public List findByOrgLevel(int orgLevel, Date timeStart, Date timeEnd) {
		return accountDAO.findByOrgLevel(orgLevel, timeStart, timeEnd, -1, -1);
	}

	// ��ѯΪĳһ����Ļ�����ֵ���缯�ţ��������˵�������
	//
	//
	public int getCountByOrgLevel(int orgLevel, Date timeStart, Date timeEnd) {
		return accountDAO.getCountByOrgLevel(orgLevel, timeStart, timeEnd);
	}
}
