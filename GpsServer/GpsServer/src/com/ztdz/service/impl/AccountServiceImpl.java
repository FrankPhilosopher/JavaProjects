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

	// 增加一张账单
	public void addAccount(TAccount account) {
		accountDAO.save(account);
	}

	// 按主键删除一张账单
	public void deleteAccount(Integer id) {
		accountDAO.delete(accountDAO.findById(id));
	}

	// 修改一张账单
	public void updateAccount(TAccount account) {
		accountDAO.attachDirty(account);
	}

	// 按主键查找一张账单
	public TAccount findById(int id) {
		return accountDAO.findById(id);
	}

	// 查找所有账单 firstResult是从0开始的(不分页)
	public List findAll() {
		return accountDAO.findAll(-1, -1);
	}

	// 查找所有账单 firstResult是从0开始的(分页)
	public List findAll(int firstResult, int maxResults) {
		return accountDAO.findAll(firstResult, maxResults);
	}

	// 查找所有账单的总数
	public int getCountAll() {
		return accountDAO.getCountAll();
	}

	// 按付账人姓名查询 其付账的所有账单(分页)
	public List findByPaider(String paiderName, int firstResult, int maxResults) {
		return accountDAO.findByPaider(paiderName, firstResult, maxResults);
	}

	// 按机构ID查询其下的的所有账单(分页)
	public List findByOrgId(int orgId, int firstResult, int maxResults) {
		return accountDAO.findByOrgId(orgId, firstResult, maxResults);
	}

	// 查询一段时间内所有的账单信息(分页) firstResult是从0开始的
	public List findAllByTime(Date timeStart, Date timeEnd, int firstResult,
			int maxResults) {
		return accountDAO.findAllByTime(timeStart, timeEnd, firstResult,
				maxResults);
	}

	// 查询一段时间内某一机构ID下的所有账单(分页)
	public List findByOrgIdByTime(int orgId, Date timeStart, Date timeEnd,
			int firstResult, int maxResults) {
		return accountDAO.findByOrgIdByTime(orgId, timeStart, timeEnd,
				firstResult, maxResults);
	}

	// 查询一段时间内某一付账人 付过账的所有账单(分页)
	public List findByPaiderByTime(String paider, Date timeStart, Date timeEnd,
			int firstResult, int maxResults) {
		return accountDAO.findByPaiderByTime(paider, timeStart, timeEnd,
				firstResult, maxResults);
	}

	// 按付账人姓名查询 其付账的所有账单(不分页)
	public List findByPaider(String paiderName) {
		return accountDAO.findByPaider(paiderName, -1, -1);
	}

	// 按机构ID查询其下的的所有账单(不分页)
	public List findByOrgId(int orgId) {
		return accountDAO.findByOrgId(orgId, -1, -1);
	}

	// 查询一段时间内所有的账单信息(不分页) firstResult是从0开始的
	public List findAllByTime(Date timeStart, Date timeEnd) {
		return accountDAO.findAllByTime(timeStart, timeEnd, -1, -1);
	}

	// 查询一段时间内某一机构ID下的所有账单(不分页)
	public List findByOrgIdByTime(int orgId, Date timeStart, Date timeEnd) {
		return accountDAO.findByOrgIdByTime(orgId, timeStart, timeEnd, -1, -1);
	}

	// 查询一段时间内某一付账人 付过账的所有账单(不分页)
	public List findByPaiderByTime(String paider, Date timeStart, Date timeEnd) {
		return accountDAO
				.findByPaiderByTime(paider, timeStart, timeEnd, -1, -1);
	}

	// 按付账人姓名查询 其付账的所有账单的总数
	public int getCountByPaider(String paiderName) {
		return accountDAO.getCountByPaider(paiderName);
	}

	// 按机构ID查询其下的的所有账单的总数
	public int getCountByOrgId(int orgId) {
		return accountDAO.getCountByOrgId(orgId);
	}

	// 查询一段时间内所有的账单信息的总数 firstResult是从0开始的
	public int getCountAllByTime(Date timeStart, Date timeEnd) {
		return accountDAO.getCountAllByTime(timeStart, timeEnd);
	}

	// 查询一段时间内某一机构ID下的所有账单的总数
	public int getCountByOrgIdByTime(int orgId, Date timeStart, Date timeEnd) {
		return accountDAO.getCountByOrgIdByTime(orgId, timeStart, timeEnd);
	}

	// 查询一段时间内某一付账人 付过账的所有账单的总数
	public int getCountByPaiderByTime(String paider, Date timeStart,
			Date timeEnd) {
		return accountDAO.getCountByPaiderByTime(paider, timeStart, timeEnd);
	}

	// 查询一段时间为某一级别的机构充值（如集团）的所有账单（分页）
	public List findByOrgLevel(int orgLevel, Date timeStart, Date timeEnd,
			int firstResult, int maxResults) {
		return accountDAO.findByOrgLevel(orgLevel, timeStart, timeEnd,
				firstResult, maxResults);
	}

	// 查询一段时间为某一级别的机构充值（如集团）的所有账单（不分页）

	public List findByOrgLevel(int orgLevel, Date timeStart, Date timeEnd) {
		return accountDAO.findByOrgLevel(orgLevel, timeStart, timeEnd, -1, -1);
	}

	// 查询为某一级别的机构充值（如集团）的所有账单的总数
	//
	//
	public int getCountByOrgLevel(int orgLevel, Date timeStart, Date timeEnd) {
		return accountDAO.getCountByOrgLevel(orgLevel, timeStart, timeEnd);
	}
}
