package com.ztdz.dao.impl;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ztdz.pojo.TCarInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * TCarInfo entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.ztdz.dao.TCarInfo
 * @author MyEclipse Persistence Tools
 */

public class TCarInfoDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TCarInfoDAO.class);
	// property constants
	public static final String TYPE_NAME = "typeName";
	public static final String OUTLINE = "outline";
	public static final String ONLINE_ON = "onlineOn";
	public static final String ONLINE_STOP = "onlineStop";
	public static final String ONLINE_WORK = "onlineWork";
	public static final String R1 = "r1";
	public static final String REMARK = "remark";

	protected void initDao() {
		// do nothing
	}

	public void save(TCarInfo transientInstance) {
		log.debug("saving TCarInfo instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TCarInfo persistentInstance) {
		log.debug("deleting TCarInfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TCarInfo findById(java.lang.Integer id) {
		log.debug("getting TCarInfo instance with id: " + id);
		try {
			TCarInfo instance = (TCarInfo) getHibernateTemplate().get(
					"com.ztdz.pojo.TCarInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TCarInfo> findByExample(TCarInfo instance) {
		log.debug("finding TCarInfo instance by example");
		try {
			List<TCarInfo> results = (List<TCarInfo>) getHibernateTemplate()
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
		log.debug("finding TCarInfo instance with property: "
				+ propertyName + ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TCarInfo.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);
			
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public int getCountByProperty(String propertyName, Object value) {
		log.debug("finding TCarInfo instance with property: "
				+ propertyName + ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TCarInfo.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTypeName(Object typeName, int firstResult,
			int maxResults) {
		return findByProperty(TYPE_NAME, typeName, firstResult, maxResults);
	}
	public int getCountByTypeName(Object typeName) {
		return getCountByProperty(TYPE_NAME, typeName);
	}

	public List findByOutline(Object outline, int firstResult,
			int maxResults) {
		return findByProperty(OUTLINE, outline, firstResult, maxResults);
	}
	public int getCountByOutline(Object outline) {
		return getCountByProperty(OUTLINE, outline);
	}

	public List findByOnlineOn(Object onlineOn, int firstResult,
			int maxResults) {
		return findByProperty(ONLINE_ON, onlineOn, firstResult, maxResults);
	}
	public int getCountByOnlineOn(Object onlineOn) {
		return getCountByProperty(ONLINE_ON, onlineOn);
	}

	public List findByOnlineStop(Object onlineStop, int firstResult,
			int maxResults) {
		return findByProperty(ONLINE_STOP, onlineStop, firstResult, maxResults);
	}
	public int getCountByOnlineStop(Object onlineStop) {
		return getCountByProperty(ONLINE_STOP, onlineStop);
	}

	public List findByOnlineWork(Object onlineWork, int firstResult,
			int maxResults) {
		return findByProperty(ONLINE_WORK, onlineWork, firstResult, maxResults);
	}
	public int getCountByOnlineWork(Object onlineWork) {
		return getCountByProperty(ONLINE_WORK, onlineWork);
	}

	public List findByR1(Object r1, int firstResult, int maxResults) {
		return findByProperty(R1, r1, firstResult, maxResults);
	}
	public int getCountByR1(Object r1) {
		return getCountByProperty(R1, r1);
	}

	public List findByRemark(Object remark, int firstResult,
			int maxResults) {
		return findByProperty(REMARK, remark, firstResult, maxResults);
	}
	public int getCountByRemark(Object remark) {
		return getCountByProperty(REMARK, remark);
	}

	public List findAll(int firstResult, int maxResults) {
		log.debug("finding all TCarInfo instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TCarInfo.class);
			return getHibernateTemplate().findByCriteria(
					detachedCriteria, firstResult, maxResults);
			
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public int getCountAll() {
		log.debug("finding all TCarInfo instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TCarInfo.class);

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TCarInfo merge(TCarInfo detachedInstance) {
		log.debug("merging TCarInfo instance");
		try {
			TCarInfo result = (TCarInfo) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TCarInfo instance) {
		log.debug("attaching dirty TCarInfo instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TCarInfo instance) {
		log.debug("attaching clean TCarInfo instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TCarInfoDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TCarInfoDAO) ctx.getBean("TCarInfoDAO");
	}
}