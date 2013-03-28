package com.rcp.wxh.dao.inter;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

/**
 * Data access interface for domain model
 * 
 * @author MyEclipse Persistence Tools
 */
public interface IBaseHibernateDAO {

	/**
	 * 获取会话
	 * 
	 * @return
	 */
	public Session getSession(); // 获取会话

	/**
	 * 关闭会话
	 */
	public void closeSession(Session session);

	/**
	 * 保存或更新实体
	 */
	public void saveOrUpdate(Object obj) throws Exception;

	/**
	 * 新增对象
	 * 
	 * @param obj
	 */
	public void save(Object obj);

	/**
	 * 删除对象
	 * 
	 * @param obj
	 */
	public void delte(Object obj);

	/**
	 * 更新对象
	 * 
	 * @param obj
	 */
	public void update(Object obj);

	/**
	 * 根据主键获取对象
	 * 
	 * @param entity
	 *            name
	 * @param id
	 * @return
	 */
	public Object findById(Class ObjName, Serializable id);

	/**
	 * 根据参数列表获取对象
	 * 
	 * @param sql
	 * @param paramValues
	 * @return
	 */
	public Object findByParas(String sql, Object[] paramValues);

	/**
	 * 根据参数列表获取对象集合
	 * 
	 * @param ObjName
	 *            entity name
	 * @param paramValues
	 * @return
	 */
	public List<Object> findByProperties(String sql, Object[] paramValues);

	/**
	 * 获取所有对象集合
	 * 
	 * @return
	 */
	public List<Object> findAll(Class ObjName);

	/**
	 * 分页查询获取集合对象
	 * 
	 * @param ObjName
	 *            entity name
	 * @param start
	 *            起始页
	 * @param offset
	 *            每页显示数目
	 * @param paramValues
	 *            参数列表
	 * @return
	 */
	public List<Object> findByPages(String sql, int start, int offset, Object[] paramValues);

}