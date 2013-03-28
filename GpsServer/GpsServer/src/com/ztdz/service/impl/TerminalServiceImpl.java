package com.ztdz.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.ztdz.dao.impl.TTerminalDAO;
import com.ztdz.pojo.TTerminal;

public class TerminalServiceImpl {
	private TTerminalDAO terminalDAO;

	public TerminalServiceImpl() {
	}

	public void setTerminalDAO(TTerminalDAO terminalDAO) {
		this.terminalDAO = terminalDAO;
	}

	public TTerminalDAO getTerminalDAO() {
		return terminalDAO;
	}

	// ����һ���ն���Ϣ
	public void addTerminal(TTerminal terminal) {
		terminalDAO.save(terminal);
	}

	// ������ɾ��һ���ն���Ϣ
	public void delTerminalById(Integer id) {
		terminalDAO.delete(terminalDAO.findById(id));
	}

	// ���ն˱��ɾ��һ���ն���Ϣ
	public void delTerminalBySim(String sim) {
		List<TTerminal> list = terminalDAO.findBySim(sim, -1, -1);
		if (list.size() != 0)
			terminalDAO.delete(list.get(0));
	}

	// �޸�һ���ն���Ϣ
	public void updateTerminal(TTerminal terminal) {
		terminalDAO.attachDirty(terminal);
	}

	// ����������һ���ն���Ϣ
	public TTerminal findById(int id) {
		return terminalDAO.findById(id);
	}

	// ���������ն���Ϣ(����ҳ)
	public List<TTerminal> findAll() {
		return terminalDAO.findAll(-1, -1);
	}

	// ���������ն���Ϣ(��ҳ)
	public List<TTerminal> findAll(int firstResult, int maxResults) {
		return terminalDAO.findAll(firstResult, maxResults);
	}

	// ���ն˱�Ų����ն���Ϣ
	public TTerminal findBySim(String sim) {
		List<TTerminal> list = terminalDAO.findBySim(sim, -1, 1);
		if (list.size() != 0)
			return list.get(0);
		return null;
	}

	// ���ն��ֻ���������ն���Ϣ
	public TTerminal findByPhone(String phone) {
		List<TTerminal> list = terminalDAO.findByPhone(phone, -1, 1);
		if (list.size() != 0)
			return list.get(0);
		return null;
	}

	public TTerminal findByUsername(String username) {
		List<TTerminal> list = terminalDAO.findByUsername(username, -1, -1);
		if (list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	public TTerminal findByPrincipal(String principal) {
		List<TTerminal> list = terminalDAO.findByPrincipal(principal, -1, -1);
		if (list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	public TTerminal findByCarnumber(String carnumber) {
		List<TTerminal> list = terminalDAO.findByCarnumber(carnumber, -1, -1);
		if (list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	// ���������ն���Ϣ������
	public int getCountAll() {
		return terminalDAO.getCountAll();
	}

	// ����һ�����飨group���µ������ն���Ϣ(����ҳ)
	public List<TTerminal> findByOrg(int orgId) {
		return terminalDAO.findByOrg(orgId, -1, -1);
	}

	// ����һ�����飨group���µ������ն���Ϣ(��ҳ)
	public List<TTerminal> findByOrg(int orgId, int firstResult, int maxResults) {
		return terminalDAO.findByOrg(orgId, firstResult, maxResults);
	}

	// ����һ�����飨group���µ������ն���Ϣ������
	public int getCountByOrg(int orgId) {
		return terminalDAO.getCountByOrg(orgId);
	}

	// //----hjw

	// ����һ�����飨group���µ������ն���Ϣ(����ҳ)
	public List<TTerminal> findMohuByOrgAndSim(String sim, int orgId) {
		return terminalDAO.findMohuByOrgAndSim(sim, orgId, -1, -1);
	}

	// ����һ�����飨group���µ������ն���Ϣ(��ҳ)
	public List<TTerminal> findMohuByOrgAndSim(String sim, int orgId, int firstResult, int maxResults) {
		return terminalDAO.findMohuByOrgAndSim(sim, orgId, firstResult, maxResults);
	}

	// ����һ�����飨group���µ������ն���Ϣ������
	public int getCountMohuByOrgAndSim(String sim, int orgId) {
		return terminalDAO.getCountMohuByOrgAndSim(sim, orgId);
	}

	// //----hjw

	// ������ID����ͬһ���͵������ն���Ϣ(����ҳ)
	public List<TTerminal> findByCarTypeId(int carTypeId) {
		return terminalDAO.findByCarTypeId(carTypeId, -1, -1);
	}

	// ������ID����ͬһ���͵������ն���Ϣ(��ҳ)
	public List<TTerminal> findByCarTypeId(Integer carTypeId, int firstResult, int maxResults) {
		return terminalDAO.findByCarTypeId(carTypeId, firstResult, maxResults);
	}

	// ������ID����ͬһ���͵������ն���Ϣ������
	public int getCountByCarTypeId(int carTypeId) {
		return terminalDAO.getCountByCarTypeId(carTypeId);
	}

	// �����ͣ��磺�ھ����������������ͬһ���͵������ն���Ϣ(����ҳ)
	public List<TTerminal> findByTypeName(String typeName) {
		return terminalDAO.findByTypeName(typeName, -1, -1);
	}

	// �����ͣ��磺�ھ����������������ͬһ���͵������ն���Ϣ(��ҳ)
	public List<TTerminal> findByTypeName(String typeName, int firstResult, int maxResults) {
		return terminalDAO.findByTypeName(typeName, firstResult, maxResults);
	}

	// �����ͣ��磺�ھ����������������ͬһ���͵������ն���Ϣ������
	public int getCountByCarTypeId(String typeName) {
		return terminalDAO.getCountByTypeName(typeName);
	}

	// ��ȼ�����Ͳ���ͬһȼ�����͵������ն���Ϣ(����ҳ)
	public List<TTerminal> findByGas(String gas) {
		return terminalDAO.findByGas(gas, -1, -1);
	}

	// ��ȼ�����Ͳ���ͬһȼ�����͵������ն���Ϣ(��ҳ)
	public List<TTerminal> findByGas(String gas, int firstResult, int maxResults) {
		return terminalDAO.findByGas(gas, firstResult, maxResults);
	}

	// ��ȼ�����Ͳ���ͬһȼ�����͵������ն���Ϣ(����ҳ)
	public int getCountByGas(String gas) {
		return terminalDAO.getCountByGas(gas);
	}

	// ����һ������ ����һ�����飨group���µ������ն���Ϣ������������û�����(��ҳ)
	public List findMohuByOrgAndUserName(String username, int orgId, int firstResult, int maxResults) {
		return terminalDAO.findMohuByOrgAndUserName(username, orgId, firstResult, maxResults);
	}

	// ����һ������ ����һ�����飨group���µ������ն˵�����������������û�����
	public int getCountMohuByOrgAndUserName(String username, int orgId) {
		return terminalDAO.getCountMohuByOrgAndUserName(username, orgId);
	}

	// ����һ������ ����һ�����飨group���µ������ն���Ϣ����������ĳ������(��ҳ)
	public List findMohuByOrgAndCarNumber(String carnumber, int orgId, int firstResult, int maxResults) {
		return terminalDAO.findMohuByOrgAndCarNumber(carnumber, orgId, firstResult, maxResults);
	}

	// ����һ������ ����һ�����飨group���µ������ն˵���������������ĳ������
	public int getCountMohuByOrgAndCarNumber(String carnumber, int orgId) {
		return terminalDAO.getCountMohuByOrgAndCarNumber(carnumber, orgId);
	}

	// ����һ������ ����һ�����飨group���µ������ն���Ϣ�����������ծȨ������(��ҳ)
	public List findMohuByOrgAndPrincipal(String principal, int orgId, int firstResult, int maxResults) {
		return terminalDAO.findMohuByOrgAndPrincipal(principal, orgId, firstResult, maxResults);
	}

	// ����һ������ ����һ�����飨group���µ������ն˵����������������ծȨ������
	public int getCountMohuByOrgAndPrincipal(String principal, int orgId) {
		return terminalDAO.getCountMohuByOrgAndPrincipal(principal, orgId);
	}

	// //////////////////////------hjw-------////////////////////////////////////////
	public int getCountByCriteria(DetachedCriteria criteria) {
		return terminalDAO.getCountByCriteria(criteria);
	}

	public List<TTerminal> findAllByCriteria(DetachedCriteria criteria) {
		return terminalDAO.findByCriteria(criteria, -1, -1);
	}

	public List<TTerminal> findAllByCriteria(DetachedCriteria criteria, int firstResult, int lastResult) {
		return terminalDAO.findByCriteria(criteria, firstResult, lastResult);
	}
	// ����һ������ �� ����ID����ͬһ���ŵ������ն���Ϣ(��ҳ)
	//
	//
	public List findByJituanId(int orgId, int firstResult, int maxResults) {
		return terminalDAO.findByJituanId(orgId, firstResult, maxResults);
	}
	// ����һ������ �� ����ID����ͬһ���ŵ������ն���Ϣ������
	//
	//
	public int getCountByJituanId(int orgId) {
		return terminalDAO.getCountByJituanId(orgId);
	}

	// //////////////////////------hjw-------////////////////////////////////////////

}
