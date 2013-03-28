package com.ztdz.dao.impl;

import java.util.Date;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ztdz.pojo.TResponse;

/**
 * A data access object (DAO) providing persistence and search support for
 * TResponse entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.ztdz.dao.impl.TResponse
 * @author MyEclipse Persistence Tools
 */

public class TResponseDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TResponseDAO.class);
	// property constants
	public static final String RESPONSE = "response";

	protected void initDao() {
		// do nothing
	}

	public void save(TResponse transientInstance) {
		log.debug("saving TResponse instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TResponse persistentInstance) {
		log.debug("deleting TResponse instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TResponse findById(java.lang.String id) {
		log.debug("getting TResponse instance with id: " + id);
		try {
			TResponse instance = (TResponse) getHibernateTemplate().get(
					"com.ztdz.pojo.TResponse", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TResponse> findByExample(TResponse instance) {
		log.debug("finding TResponse instance by example");
		try {
			List<TResponse> results = (List<TResponse>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TResponse instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TResponse as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TResponse> findByResponse(Object response) {
		return findByProperty(RESPONSE, response);
	}

	public List findAll() {
		log.debug("finding all TResponse instances");
		try {
			String queryString = "from TResponse";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TResponse merge(TResponse detachedInstance) {
		log.debug("merging TResponse instance");
		try {
			TResponse result = (TResponse) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TResponse instance) {
		log.debug("attaching dirty TResponse instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TResponse instance) {
		log.debug("attaching clean TResponse instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TResponseDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TResponseDAO) ctx.getBean("TResponseDAO");
	}
}