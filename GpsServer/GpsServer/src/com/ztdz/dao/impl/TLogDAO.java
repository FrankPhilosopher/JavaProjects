package com.ztdz.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ztdz.pojo.TLog;

/**
 * A data access object (DAO) providing persistence and search support for TLog
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.ztdz.dao.TLog
 * @author MyEclipse Persistence Tools
 */

public class TLogDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(TLogDAO.class);
	// property constants
	public static final String LOG_EVENT = "logEvent";

	protected void initDao() {
		// do nothing
	}

	public void save(TLog transientInstance) {
		log.debug("saving TLog instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TLog persistentInstance) {
		log.debug("deleting TLog instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TLog findById(java.lang.Integer id) {
		log.debug("getting TLog instance with id: " + id);
		try {
			TLog instance = (TLog) getHibernateTemplate().get(
					"com.ztdz.pojo.TLog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TLog> findByExample(TLog instance) {
		log.debug("finding TLog instance by example");
		try {
			List<TLog> results = (List<TLog>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value,
			int firstResult, int maxResults) {
		log.debug("finding TLog instance with property: " + propertyName
				+ ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TLog.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public int getCountByProperty(String propertyName, Object value) {
		log.debug("finding TLog instance with property: " + propertyName
				+ ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TLog.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByLogEvent(Object logEvent, int firstResult, int maxResults) {
		return findByProperty(LOG_EVENT, logEvent, firstResult, maxResults);
	}

	public int getCountByLogEvent(Object logEvent) {
		return getCountByProperty(LOG_EVENT, logEvent);
	}

	public List findAll(int firstResult, int maxResults) {
		log.debug("finding all TLog instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TLog.class);
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public int getCountAll() {
		log.debug("finding all TLog instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TLog.class);

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TLog merge(TLog detachedInstance) {
		log.debug("merging TLog instance");
		try {
			TLog result = (TLog) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TLog instance) {
		log.debug("attaching dirty TLog instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TLog instance) {
		log.debug("attaching clean TLog instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TLogDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TLogDAO) ctx.getBean("TLogDAO");
	}

	// 按时间区间查询日志(分页)
	//
	//
	//
	public List findByTimeBetween(Date timeStart, Date timeEnd,
			int firstResult, int maxResults) {
		log.debug("Timestart: " + timeStart + ", TimeEnd " + timeEnd);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TLog.class);
			detachedCriteria.add(
					Restrictions.between("logTime", timeStart, timeEnd))
					.addOrder(Order.desc("logTime"));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 按时间区间查询区间内日志总数
	//
	//
	//
	public int getCountByTimeBetween(Date timeStart, Date timeEnd) {
		log.debug("Timestart: " + timeStart + ", TimeEnd " + timeEnd);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TLog.class);
			detachedCriteria.add(Restrictions.between("logTime", timeStart,
					timeEnd));

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 增加一个按操作员id（userid）查询日志的方法(分页)
	//
	//
	public List findByUserId(String userId, int firstResult, int maxResults) {
		log.debug("finding TLog instance with property: " + userId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TLog.class);
			detachedCriteria.createAlias("TUser", "u").add(
					Restrictions.eq("u.userid", userId)).addOrder(Order.desc("logTime"));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 增加一个按操作员id（userid）查询日志总数的方法
	//
	//
	public int getCountByUserId(String userId) {
		log.debug("finding TLog instance with property: " + userId);

		// return findByProperty("TUser.userid", userId, firstResult,
		// maxResults);
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TLog.class);
			detachedCriteria.createAlias("TUser", "u").add(
					Restrictions.eq("u.userid", userId));

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 增加一个按操作员id（userid）和时间区间查询日志的方法(分页)
	//
	//
	public List findByUserIdByTimeBetween(String userId, Date timeStart,
			Date timeEnd, int firstResult, int maxResults) {
		log.debug("finding TLog instance with property: " + userId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TLog.class);
			detachedCriteria.createAlias("TUser", "u")
					.add(Restrictions.eq("u.userid", userId))
					.add(Restrictions.between("logTime", timeStart, timeEnd)).addOrder(Order.desc("logTime"));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// 增加一个按操作员id（userid）和时间区间查询日志总数的方法
	//
	//
	public int getCountByUserIdByTimeBetween(String userId, Date timeStart,
			Date timeEnd) {
		log.debug("finding TLog instance with property: " + userId);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TLog.class);
			detachedCriteria.createAlias("TUser", "u")
					.add(Restrictions.eq("u.userid", userId))
					.add(Restrictions.between("logTime", timeStart, timeEnd));

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}