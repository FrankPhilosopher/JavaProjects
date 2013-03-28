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

	// 增加一个终端信息
	public void addTerminal(TTerminal terminal) {
		terminalDAO.save(terminal);
	}

	// 按主键删除一个终端信息
	public void delTerminalById(Integer id) {
		terminalDAO.delete(terminalDAO.findById(id));
	}

	// 按终端编号删除一个终端信息
	public void delTerminalBySim(String sim) {
		List<TTerminal> list = terminalDAO.findBySim(sim, -1, -1);
		if (list.size() != 0)
			terminalDAO.delete(list.get(0));
	}

	// 修改一个终端信息
	public void updateTerminal(TTerminal terminal) {
		terminalDAO.attachDirty(terminal);
	}

	// 按主键查找一个终端信息
	public TTerminal findById(int id) {
		return terminalDAO.findById(id);
	}

	// 查找所有终端信息(不分页)
	public List<TTerminal> findAll() {
		return terminalDAO.findAll(-1, -1);
	}

	// 查找所有终端信息(分页)
	public List<TTerminal> findAll(int firstResult, int maxResults) {
		return terminalDAO.findAll(firstResult, maxResults);
	}

	// 按终端编号查找终端信息
	public TTerminal findBySim(String sim) {
		List<TTerminal> list = terminalDAO.findBySim(sim, -1, 1);
		if (list.size() != 0)
			return list.get(0);
		return null;
	}

	// 按终端手机号码查找终端信息
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

	// 查找所有终端信息的总数
	public int getCountAll() {
		return terminalDAO.getCountAll();
	}

	// 查找一个分组（group）下的所有终端信息(不分页)
	public List<TTerminal> findByOrg(int orgId) {
		return terminalDAO.findByOrg(orgId, -1, -1);
	}

	// 查找一个分组（group）下的所有终端信息(分页)
	public List<TTerminal> findByOrg(int orgId, int firstResult, int maxResults) {
		return terminalDAO.findByOrg(orgId, firstResult, maxResults);
	}

	// 查找一个分组（group）下的所有终端信息的总数
	public int getCountByOrg(int orgId) {
		return terminalDAO.getCountByOrg(orgId);
	}

	// //----hjw

	// 查找一个分组（group）下的所有终端信息(不分页)
	public List<TTerminal> findMohuByOrgAndSim(String sim, int orgId) {
		return terminalDAO.findMohuByOrgAndSim(sim, orgId, -1, -1);
	}

	// 查找一个分组（group）下的所有终端信息(分页)
	public List<TTerminal> findMohuByOrgAndSim(String sim, int orgId, int firstResult, int maxResults) {
		return terminalDAO.findMohuByOrgAndSim(sim, orgId, firstResult, maxResults);
	}

	// 查找一个分组（group）下的所有终端信息的总数
	public int getCountMohuByOrgAndSim(String sim, int orgId) {
		return terminalDAO.getCountMohuByOrgAndSim(sim, orgId);
	}

	// //----hjw

	// 按类型ID查找同一类型的所有终端信息(不分页)
	public List<TTerminal> findByCarTypeId(int carTypeId) {
		return terminalDAO.findByCarTypeId(carTypeId, -1, -1);
	}

	// 按类型ID查找同一类型的所有终端信息(分页)
	public List<TTerminal> findByCarTypeId(Integer carTypeId, int firstResult, int maxResults) {
		return terminalDAO.findByCarTypeId(carTypeId, firstResult, maxResults);
	}

	// 按类型ID查找同一类型的所有终端信息的总数
	public int getCountByCarTypeId(int carTypeId) {
		return terminalDAO.getCountByCarTypeId(carTypeId);
	}

	// 按类型（如：挖掘机，推土机）查找同一类型的所有终端信息(不分页)
	public List<TTerminal> findByTypeName(String typeName) {
		return terminalDAO.findByTypeName(typeName, -1, -1);
	}

	// 按类型（如：挖掘机，推土机）查找同一类型的所有终端信息(分页)
	public List<TTerminal> findByTypeName(String typeName, int firstResult, int maxResults) {
		return terminalDAO.findByTypeName(typeName, firstResult, maxResults);
	}

	// 按类型（如：挖掘机，推土机）查找同一类型的所有终端信息的总数
	public int getCountByCarTypeId(String typeName) {
		return terminalDAO.getCountByTypeName(typeName);
	}

	// 按燃油类型查找同一燃油类型的所有终端信息(不分页)
	public List<TTerminal> findByGas(String gas) {
		return terminalDAO.findByGas(gas, -1, -1);
	}

	// 按燃油类型查找同一燃油类型的所有终端信息(分页)
	public List<TTerminal> findByGas(String gas, int firstResult, int maxResults) {
		return terminalDAO.findByGas(gas, firstResult, maxResults);
	}

	// 按燃油类型查找同一燃油类型的所有终端信息(不分页)
	public int getCountByGas(String gas) {
		return terminalDAO.getCountByGas(gas);
	}

	// 增加一个方法 查找一个分组（group）下的所有终端信息，根据输入的用户名称(分页)
	public List findMohuByOrgAndUserName(String username, int orgId, int firstResult, int maxResults) {
		return terminalDAO.findMohuByOrgAndUserName(username, orgId, firstResult, maxResults);
	}

	// 增加一个方法 查找一个分组（group）下的所有终端的总数，根据输入的用户名称
	public int getCountMohuByOrgAndUserName(String username, int orgId) {
		return terminalDAO.getCountMohuByOrgAndUserName(username, orgId);
	}

	// 增加一个方法 查找一个分组（group）下的所有终端信息，根据输入的车辆编号(分页)
	public List findMohuByOrgAndCarNumber(String carnumber, int orgId, int firstResult, int maxResults) {
		return terminalDAO.findMohuByOrgAndCarNumber(carnumber, orgId, firstResult, maxResults);
	}

	// 增加一个方法 查找一个分组（group）下的所有终端的总数，根据输入的车辆编号
	public int getCountMohuByOrgAndCarNumber(String carnumber, int orgId) {
		return terminalDAO.getCountMohuByOrgAndCarNumber(carnumber, orgId);
	}

	// 增加一个方法 查找一个分组（group）下的所有终端信息，根据输入的债权人姓名(分页)
	public List findMohuByOrgAndPrincipal(String principal, int orgId, int firstResult, int maxResults) {
		return terminalDAO.findMohuByOrgAndPrincipal(principal, orgId, firstResult, maxResults);
	}

	// 增加一个方法 查找一个分组（group）下的所有终端的总数，根据输入的债权人姓名
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
	// 增加一个方法 按 集团ID查找同一集团的所有终端信息(分页)
	//
	//
	public List findByJituanId(int orgId, int firstResult, int maxResults) {
		return terminalDAO.findByJituanId(orgId, firstResult, maxResults);
	}
	// 增加一个方法 按 集团ID查找同一集团的所有终端信息的总数
	//
	//
	public int getCountByJituanId(int orgId) {
		return terminalDAO.getCountByJituanId(orgId);
	}

	// //////////////////////------hjw-------////////////////////////////////////////

}
