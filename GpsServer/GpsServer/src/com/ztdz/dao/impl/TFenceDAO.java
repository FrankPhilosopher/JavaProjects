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

import com.ztdz.pojo.TFence;

/**
 * A data access object (DAO) providing persistence and search support for
 * TFence entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.ztdz.dao.TFence
 * @author MyEclipse Persistence Tools
 */

public class TFenceDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(TFenceDAO.class);
	// property constants
	public static final String ONOFF = "onoff";
	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";
	public static final String RADIOUS = "radious";

	protected void initDao() {
		// do nothing
	}

	public void save(TFence transientInstance) {
		log.debug("saving TFence instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TFence persistentInstance) {
		log.debug("deleting TFence instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TFence findById(java.lang.Integer id) {
		log.debug("getting TFence instance with id: " + id);
		try {
			TFence instance = (TFence) getHibernateTemplate().get(
					"com.ztdz.pojo.TFence", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TFence> findByExample(TFence instance) {
		log.debug("finding TFence instance by example");
		try {
			List<TFence> results = (List<TFence>) getHibernateTemplate()
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
		log.debug("finding TFence instance with property: "
				+ propertyName + ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TFence.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);
			
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public int getCountByProperty(String propertyName, Object value) {
		log.debug("finding TFence instance with property: "
				+ propertyName + ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TFence.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByOnoff(Object onoff, int firstResult,
			int maxResults) {
		return findByProperty(ONOFF, onoff, firstResult, maxResults);
	}
	public int getCountByOnoff(Object onoff) {
		return getCountByProperty(ONOFF, onoff);
	}

	public List findByLongitude(Object longitude, int firstResult,
			int maxResults) {
		return findByProperty(LONGITUDE, longitude, firstResult, maxResults);
	}
	public int getCountByLongitude(Object longitude) {
		return getCountByProperty(LONGITUDE, longitude);
	}

	public List findByLatitude(Object latitude, int firstResult,
			int maxResults) {
		return findByProperty(LATITUDE, latitude, firstResult, maxResults);
	}
	public int getCountByLatitude(Object latitude) {
		return getCountByProperty(LATITUDE, latitude);
	}

	public List findByRadious(Object radious, int firstResult,
			int maxResults) {
		return findByProperty(RADIOUS, radious, firstResult, maxResults);
	}
	public int getCountByRadious(Object radious) {
		return getCountByProperty(RADIOUS, radious);
	}

	public List findAll(int firstResult, int maxResults) {
		log.debug("finding all TFence instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TFence.class);
			return getHibernateTemplate().findByCriteria(
					detachedCriteria, firstResult, maxResults);
			
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public int getCountAll() {
		log.debug("finding all TFence instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TFence.class);

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TFence merge(TFence detachedInstance) {
		log.debug("merging TFence instance");
		try {
			TFence result = (TFence) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TFence instance) {
		log.debug("attaching dirty TFence instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TFence instance) {
		log.debug("attaching clean TFence instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TFenceDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TFenceDAO) ctx.getBean("TFenceDAO");
	}

	public List<TFence> findBySim(String sim) {
		log.debug("finding TOrgainzation instance with property: "
				+ ", value: " + sim);
		try {
			String queryString = "from TFence as model where model.TTerminal.sim=?";
			return getHibernateTemplate().find(queryString, sim);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}