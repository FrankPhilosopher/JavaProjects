package com.ztdz.service.impl;

import java.util.List;

import com.ztdz.dao.impl.TUserDAO;
import com.ztdz.pojo.TUser;

/**
 * 用户信息服务层
 * 
 * @author wuxuehong
 * 
 *         2012-5-15
 */
public class UserServiceImpl {

	/**
	 * 注入dao层服务对象
	 */
	private TUserDAO userDao;

	public UserServiceImpl() {
		super();
	}

	/**
	 * 用户登陆验证
	 * 
	 * @param user
	 * @return
	 */
	public TUser login(TUser user) {
		List<TUser> list = userDao.findByUserid(user.getUserid()); // 根据用户ID从数据库查询
		if (list == null || list.size() == 0) {
			return null;
		}
		if (list.get(0).getPwd().equals(user.getPwd()))
			return list.get(0);
		return null;
	}

	public TUserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(TUserDAO userDao) {
		this.userDao = userDao;
	}

	// 增加用户
	public void addUser(TUser tUser) {
		userDao.save(tUser);
	}

	// 按主键删除用户
	public void delUserById(Integer id) {
		userDao.delete(userDao.findById(id));
	}

	// 按userid删除用户
	public void delUserByUserId(String userid) {
		List<TUser> list = userDao.findByUserid(userid);
		if (list.size() != 0)
			userDao.delete(list.get(0));
	}

	// 修改用户信息
	public void updateUser(TUser tUser){
		userDao.attachDirty(tUser);
	}

	// 按用户名查询用户
	public TUser findByUserId(String userId) {
		List<TUser> list = userDao.findByUserid(userId);
		if (list.size() != 0)
			return list.get(0);
		return null;
	}

	// 按主键查询用户
	public TUser findById(Integer id) {
		return userDao.findById(id);
	}

	// 查询所有用户(不分页)
	public List<TUser> findAll() {
		return userDao.findAll(-1, -1);
	}

	// 查询所有用户(分页)
	public List<TUser> findAll(int firstResult, int maxResults) {
		return userDao.findAll(firstResult, maxResults);
	}

	// 查询所有用户的总数
	public int getCountAll() {
		return userDao.getCountAll();
	}

	// 查找一个集团下的所有账户
	public List<TUser> findByOrgId(int orgId) {
		return userDao.findByOrgId(orgId);
	}
}
