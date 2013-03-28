package com.rcp.wxh.service.impl;

import java.util.List;

import com.rcp.wxh.dao.impl.TEmpDao;
import com.rcp.wxh.pojo.TEmp;

/**
 * 有关emp 对象的业务逻辑处理
 * 
 * @author JH
 *         2011 11 3
 */
public class EmpService extends ExceptionEvent {

	private final String module = "员工管理";

	/**
	 * 新增员工
	 * 
	 * @param tEmp
	 * @throws Exception
	 */
	public void addEmp(TEmp tEmp) throws Exception {
		TEmpDao tdao = new TEmpDao();
		try {
			tdao.save(tEmp);
		} catch (Exception e) {
			fireExceptionRecord("新增员工信息异常!", module);
			throw e;
		}
	}

	/**
	 * 删除员工
	 * 
	 * @param tEmp
	 * @throws Exception
	 */
	public void delEmp(TEmp tEmp) throws Exception {
		TEmpDao tdao = new TEmpDao();
		try {
			tdao.delte(tEmp);
		} catch (Exception e) {
			fireExceptionRecord("删除员工信息异常!", module);
			throw e;
		}
	}

	/**
	 * 更新账户
	 * 
	 * @param tEmp
	 * @throws Exception
	 */
	public void updateEmp(TEmp tEmp) throws Exception {
		TEmpDao tdao = new TEmpDao();
		try {
			tdao.update(tEmp);
		} catch (Exception e) {
			fireExceptionRecord("更新员工信息异常!", module);
			throw e;
		}
	}

	/**
	 * 根据用户名和密码查询员工
	 * 
	 * @param username
	 *            ,password
	 * @throws Exception
	 */
	public TEmp getEmpByNamePwd(String username, String password) throws Exception {
		TEmp emp = null;
		TEmpDao tdao = new TEmpDao();
		try {
			emp = tdao.getEmpByNamePwd(username, password);
		} catch (Exception er) {
			fireExceptionRecord("员工登录异常!", module);
			throw er;
		}
		return emp;
	}

	/**
	 * 查询所有员工 返回员工集合
	 * 
	 * @param
	 * @throws Exception
	 */
	public List<Object> getAllEmp() throws Exception {
		List<Object> allEmp = null;
		TEmpDao tdao = new TEmpDao();
		try {
			allEmp = tdao.findAll(TEmp.class);
		} catch (Exception e) {
			fireExceptionRecord("查询所有员工信息异常!", module);
			throw e;
		}
		return allEmp;
	}

	/**
	 * 查询所有操作员 返回操作员集合
	 * 
	 * @param
	 * @throws Exception
	 */
	public List<Object> getAllOpt() throws Exception {
		List<Object> allOpt = null;
		TEmpDao tdao = new TEmpDao();
		try {
			allOpt = tdao.getAllEmpByPriority(1);
		} catch (Exception e) {
			throw e;
		}
		return allOpt;
	}

	/**
	 * 查询所有管理员 返回管理员集合
	 * 
	 * @param
	 * @throws Exception
	 */
	public List<Object> getAllAdmin() throws Exception {
		List<Object> allOpt = null;
		TEmpDao tdao = new TEmpDao();
		try {
			allOpt = tdao.getAllEmpByPriority(0);
		} catch (Exception e) {
			throw e;
		}
		return allOpt;
	}

	/**
	 * 按照员工ID查询 返回某个员工 null表示无此ID
	 * 
	 * @param
	 * @throws Exception
	 */
	public TEmp getEmpById(String operatorid) throws Exception {
		TEmp emp = null;
		TEmpDao tdao = new TEmpDao();
		try {
			emp = tdao.getEmpById(operatorid);
		} catch (Exception e) {
			throw e;
		}
		return emp;
	}

	/**
	 * 按照员工姓名查询 返回同名员工集合
	 * 
	 * @param
	 * @throws Exception
	 */
	public List<Object> getEmpByName(String operatorname) throws Exception {
		List<Object> emp = null;
		TEmpDao tdao = new TEmpDao();
		try {
			emp = tdao.getEmpByName(operatorname);
		} catch (Exception e) {
			throw e;
		}
		return emp;
	}

	public List<Object> getEmpByFindExpress(String find_what, String find_express) throws Exception {
		List<Object> emp = null;
		TEmpDao tdao = new TEmpDao();
		try {
			emp = tdao.getAllEmpByFindExpress(find_what, find_express);
		} catch (Exception e) {
			throw e;
		}
		return emp;
	}
}
