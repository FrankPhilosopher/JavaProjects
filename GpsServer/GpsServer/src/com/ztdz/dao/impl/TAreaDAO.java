package com.ztdz.dao.impl;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ztdz.pojo.TArea;

/**
 * A data access object (DAO) providing persistence and search support for TArea
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.ztdz.dao.TArea
 * @author MyEclipse Persistence Tools
 */

public class TAreaDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(TAreaDAO.class);
	// property constants
	public static final String NAME = "name";

	protected void initDao() {
		// do nothing
	}

	public void save(TArea transientInstance) {
		log.debug("saving TArea instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TArea persistentInstance) {
		log.debug("deleting TArea instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TArea findById(java.lang.Integer id) {
		log.debug("getting TArea instance with id: " + id);
		try {
			TArea instance = (TArea) getHibernateTemplate().get(
					"com.ztdz.pojo.TArea", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TArea> findByExample(TArea instance) {
		log.debug("finding TArea instance by example");
		try {
			List<TArea> results = (List<TArea>) getHibernateTemplate()
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
		log.debug("finding TArea instance with property: " + propertyName
				+ ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TArea.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public int getCountByProperty(String propertyName, Object value) {
		log.debug("finding TArea instance with property: " + propertyName
				+ ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TArea.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name, int firstResult, int maxResults) {
		return findByProperty(NAME, name, firstResult, maxResults);
	}

	public int getCountByName(Object name) {
		return getCountByProperty(NAME, name);
	}

	// 得到所有省份
	public List findAll(int firstResult, int maxResults) {
		log.debug("finding all TArea instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
					TArea.class).add(Restrictions.eq("TArea.areaId", 1));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public int getCountAll() {
		log.debug("finding all TArea instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
					TArea.class).add(Restrictions.eq("TArea.areaId", 1));

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	// 找到某个省份对应的城市
	public List<TArea> getCities(int province) {
		log.debug("finding all TArea instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
					TArea.class).add(Restrictions.eq("TArea.areaId", province));
			return getHibernateTemplate().findByCriteria(detachedCriteria);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TArea merge(TArea detachedInstance) {
		log.debug("merging TArea instance");
		try {
			TArea result = (TArea) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TArea instance) {
		log.debug("attaching dirty TArea instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TArea instance) {
		log.debug("attaching clean TArea instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TAreaDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TAreaDAO) ctx.getBean("TAreaDAO");
	}
}