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

import com.ztdz.pojo.TTempLatlng;

/**
 * A data access object (DAO) providing persistence and search support for
 * TTempLatlng entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.ztdz.dao.TTempLatlng
 * @author MyEclipse Persistence Tools
 */

public class TTempLatlngDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TTempLatlngDAO.class);
	// property constants
	public static final String LAT = "lat";
	public static final String LNG = "lng";
	public static final String OFFSET_X = "offsetX";
	public static final String OFFSET_Y = "offsetY";
	public static final String OFFSET_LNG = "offsetLng";
	public static final String OFFSET_LAT = "offsetLat";

	protected void initDao() {
		// do nothing
	}

	public void save(TTempLatlng transientInstance) {
		log.debug("saving TTempLatlng instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TTempLatlng persistentInstance) {
		log.debug("deleting TTempLatlng instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TTempLatlng findById(java.lang.Integer id) {
		log.debug("getting TTempLatlng instance with id: " + id);
		try {
			TTempLatlng instance = (TTempLatlng) getHibernateTemplate().get(
					"com.ztdz.pojo.TTempLatlng", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TTempLatlng> findByExample(TTempLatlng instance) {
		log.debug("finding TTempLatlng instance by example");
		try {
			List<TTempLatlng> results = (List<TTempLatlng>) getHibernateTemplate()
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
		log.debug("finding TTempLatlng instance with property: " + propertyName
				+ ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TTempLatlng.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public int getCountByProperty(String propertyName, Object value) {
		log.debug("finding TTempLatlng instance with property: " + propertyName
				+ ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TTempLatlng.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByLat(Object lat, int firstResult, int maxResults) {
		return findByProperty(LAT, lat, firstResult, maxResults);
	}

	public List findByLng(Object lng, int firstResult, int maxResults) {
		return findByProperty(LNG, lng, firstResult, maxResults);
	}

	public List findByOffsetX(Object offsetX, int firstResult, int maxResults) {
		return findByProperty(OFFSET_X, offsetX, firstResult, maxResults);
	}

	public List findByOffsetY(Object offsetY, int firstResult, int maxResults) {
		return findByProperty(OFFSET_Y, offsetY, firstResult, maxResults);
	}

	public List findByOffsetLng(Object offsetLng, int firstResult,
			int maxResults) {
		return findByProperty(OFFSET_LNG, offsetLng, firstResult, maxResults);
	}

	public List findByOffsetLat(Object offsetLat, int firstResult,
			int maxResults) {
		return findByProperty(OFFSET_LAT, offsetLat, firstResult, maxResults);
	}

	public int getCountByLat(Object lat) {
		return getCountByProperty(LAT, lat);
	}

	public int getCountByLng(Object lng) {
		return getCountByProperty(LNG, lng);
	}

	public int getCountByOffsetX(Object offsetX) {
		return getCountByProperty(OFFSET_X, offsetX);
	}

	public int getCountByOffsetY(Object offsetY) {
		return getCountByProperty(OFFSET_Y, offsetY);
	}

	public int getCountByOffsetLng(Object offsetLng) {
		return getCountByProperty(OFFSET_LNG, offsetLng);
	}

	public int getCountByOffsetLat(Object offsetLat) {
		return getCountByProperty(OFFSET_LAT, offsetLat);
	}

	public List findAll(int firstResult, int maxResults) {
		log.debug("finding all TTempLatlng instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TTempLatlng.class);
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public int getCountAll() {
		log.debug("finding all TTempLatlng instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TTempLatlng.class);

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TTempLatlng merge(TTempLatlng detachedInstance) {
		log.debug("merging TTempLatlng instance");
		try {
			TTempLatlng result = (TTempLatlng) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TTempLatlng instance) {
		log.debug("attaching dirty TTempLatlng instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TTempLatlng instance) {
		log.debug("attaching clean TTempLatlng instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TTempLatlngDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (TTempLatlngDAO) ctx.getBean("TTempLatlngDAO");
	}
}