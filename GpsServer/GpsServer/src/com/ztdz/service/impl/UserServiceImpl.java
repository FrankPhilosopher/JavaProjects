package com.ztdz.service.impl;

import java.util.List;

import com.ztdz.dao.impl.TUserDAO;
import com.ztdz.pojo.TUser;

/**
 * �û���Ϣ�����
 * 
 * @author wuxuehong
 * 
 *         2012-5-15
 */
public class UserServiceImpl {

	/**
	 * ע��dao��������
	 */
	private TUserDAO userDao;

	public UserServiceImpl() {
		super();
	}

	/**
	 * �û���½��֤
	 * 
	 * @param user
	 * @return
	 */
	public TUser login(TUser user) {
		List<TUser> list = userDao.findByUserid(user.getUserid()); // �����û�ID�����ݿ��ѯ
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

	// �����û�
	public void addUser(TUser tUser) {
		userDao.save(tUser);
	}

	// ������ɾ���û�
	public void delUserById(Integer id) {
		userDao.delete(userDao.findById(id));
	}

	// ��useridɾ���û�
	public void delUserByUserId(String userid) {
		List<TUser> list = userDao.findByUserid(userid);
		if (list.size() != 0)
			userDao.delete(list.get(0));
	}

	// �޸��û���Ϣ
	public void updateUser(TUser tUser){
		userDao.attachDirty(tUser);
	}

	// ���û�����ѯ�û�
	public TUser findByUserId(String userId) {
		List<TUser> list = userDao.findByUserid(userId);
		if (list.size() != 0)
			return list.get(0);
		return null;
	}

	// ��������ѯ�û�
	public TUser findById(Integer id) {
		return userDao.findById(id);
	}

	// ��ѯ�����û�(����ҳ)
	public List<TUser> findAll() {
		return userDao.findAll(-1, -1);
	}

	// ��ѯ�����û�(��ҳ)
	public List<TUser> findAll(int firstResult, int maxResults) {
		return userDao.findAll(firstResult, maxResults);
	}

	// ��ѯ�����û�������
	public int getCountAll() {
		return userDao.getCountAll();
	}

	// ����һ�������µ������˻�
	public List<TUser> findByOrgId(int orgId) {
		return userDao.findByOrgId(orgId);
	}
}
