package com.rcp.wxh.dao.inter;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.rcp.wxh.sql.hibernate.MySessionFactory;

/**
 * Data access object (DAO) for domain model
 * 
 * @author MyEclipse Persistence Tools
 */
public class BaseHibernateDAO implements IBaseHibernateDAO {

	private static final Log log = LogFactory.getLog(BaseHibernateDAO.class);

	public Session getSession() {
		return MySessionFactory.getSessionFactory().openSession();
	}

	public void closeSession(Session session) {
		if (session != null)
			session.close();
	}

	@Override
	public void delte(Object obj) {
		try {
			Session session = getSession(); // 开启会话
			org.hibernate.Transaction ta = session.beginTransaction(); // 开启事物
			session.delete(obj); // 执行事物
			ta.commit(); // 提交事物
			closeSession(session); // 关闭会话
		} catch (RuntimeException re) {
			log.error("Delete object failed.");
			throw re;
		}
	}

	@Override
	public List<Object> findAll(Class ObjName) {
		List<Object> list;
		try {
			Session session = getSession(); // 开启会话
			org.hibernate.Transaction ta = session.beginTransaction(); // 开启事物
			list = session.createQuery("from " + ObjName.getName()).list();
			ta.commit(); // 提交事物
			closeSession(session); // 关闭会话
			return list;
		} catch (RuntimeException re) {
			log.error("get object list failed.");
			throw re;
		}
	}

	@Override
	public Object findById(Class ObjName, Serializable id) {
		// TODO Auto-generated method stub
		try {
			Session session = getSession(); // 开启会话
			org.hibernate.Transaction ta = session.beginTransaction(); // 开启事物
			Object obj = session.get(ObjName, id);
			ta.commit(); // 提交事物
			closeSession(session); // 关闭会话
			return obj;
		} catch (RuntimeException re) {
			log.error("find object failed.");
			throw re;
		}
	}

	@Override
	public List<Object> findByPages(String sql, int start, int offset, Object[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> findByProperties(String sql, Object[] paramValues) {
		// TODO Auto-generated method stub
		try {
			Session session = getSession(); // 开启会话
			org.hibernate.Transaction ta = session.beginTransaction(); // 开启事物
			Query query = session.createQuery(sql);
			for (int i = 0; i < paramValues.length; i++) {
				query.setParameter(i, paramValues[i]);
			}
			List<Object> list = query.list();
//			System.out.println(query.getQueryString());
			ta.commit(); // 提交事物
			closeSession(session); // 关闭会话
			return list;
		} catch (RuntimeException re) {
			log.error("find object list failed.");
			throw re;
		}
	}

	@Override
	public void save(Object obj) {
		// TODO Auto-generated method stub
		try {
			Session session = getSession(); // 开启会话
			org.hibernate.Transaction ta = session.beginTransaction(); // 开启事物
			session.save(obj); // 执行事物
			ta.commit(); // 提交事物
			closeSession(session); // 关闭会话
		} catch (RuntimeException re) {
			log.error("Save object failed.");
			throw re;
		}
	}

	@Override
	public void saveOrUpdate(Object obj) throws Exception {
		try {
			Session session = getSession(); // 开启会话
			org.hibernate.Transaction ta = session.beginTransaction(); // 开启事物
			session.saveOrUpdate(obj); // 执行事物
			ta.commit(); // 提交事物
			closeSession(session); // 关闭会话
		} catch (RuntimeException re) {
			log.error("SaveOrUpdate object failed.");
			throw re;
		}
	}

	@Override
	public void update(Object obj) {
		try {
			Session session = getSession(); // 开启会话
			org.hibernate.Transaction ta = session.beginTransaction(); // 开启事物
			session.update(obj); // 执行事物
			ta.commit(); // 提交事物
			closeSession(session); // 关闭会话
		} catch (RuntimeException re) {
			log.error("Update object failed.");
			throw re;
		}
	}

	@Override
	public Object findByParas(String sql, Object[] paramValues) {
		try {
			Session session = getSession(); // 开启会话
			org.hibernate.Transaction ta = session.beginTransaction(); // 开启事物
			Query query = session.createQuery(sql);
			for (int i = 0; i < paramValues.length; i++) {
				query.setParameter(i, paramValues[i]);
			}
			Object obj = query.uniqueResult();
			ta.commit(); // 提交事物
			closeSession(session); // 关闭会话
			return obj;
		} catch (RuntimeException re) {
			log.error("Update object failed.");
			throw re;
		}
	}

	public int getCount(String sql) throws Exception {
		int count = 0;
		try {
			Session session = getSession(); // 开启会话
			java.sql.Connection con = session.connection();
			java.sql.PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			ps.close();
			con.close();
			closeSession(session);
			return count;
		} catch (Exception re) {
			log.error("Update object failed.");
			throw re;
		}
	}

}