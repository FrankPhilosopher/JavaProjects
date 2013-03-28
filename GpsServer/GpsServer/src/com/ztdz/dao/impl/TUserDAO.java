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

import com.ztdz.pojo.TOrgainzation;
import com.ztdz.pojo.TUser;
import com.ztdz.tools.OrgLevel;

/**
 * A data access object (DAO) providing persistence and search support for TUser
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.ztdz.dao.TUser
 * @author MyEclipse Persistence Tools
 */

public class TUserDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(TUserDAO.class);
	// property constants
	public static final String USERID = "userid";
	public static final String PWD = "pwd";
	public static final String NAME = "name";
	public static final String CELLPHONE = "cellphone";
	public static final String OILELE = "oilele";
	public static final String MODIFY = "modify";
	public static final String EXPORT = "export";

	protected void initDao() {
		// do nothing
	}

	public void save(TUser transientInstance) {
		log.debug("saving TUser instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TUser persistentInstance) {
		log.debug("deleting TUser instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TUser findById(java.lang.Integer id) {
		log.debug("getting TUser instance with id: " + id);
		try {
			TUser instance = (TUser) getHibernateTemplate().get(
					"com.ztdz.pojo.TUser", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TUser> findByExample(TUser instance) {
		log.debug("finding TUser instance by example");
		try {
			List<TUser> results = (List<TUser>) getHibernateTemplate()
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
		log.debug("finding TUser instance with property: " + propertyName
				+ ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TUser.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public int getCountByProperty(String propertyName, Object value) {
		log.debug("finding TUser instance with property: " + propertyName
				+ ", value: " + value);

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TUser.class);
			detachedCriteria.add(Restrictions.eq(propertyName, value));
			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUserid(Object userid) {
		return findByProperty(USERID, userid, -1, -1);
	}

	public List findByPwd(Object pwd, int firstResult, int maxResults) {
		return findByProperty(PWD, pwd, firstResult, maxResults);
	}

	public List findByName(Object name, int firstResult, int maxResults) {
		return findByProperty(NAME, name, firstResult, maxResults);
	}

	public List findByCellphone(Object cellphone, int firstResult,
			int maxResults) {
		return findByProperty(CELLPHONE, cellphone, firstResult, maxResults);
	}

	public List findByOilele(Object oilele, int firstResult, int maxResults) {
		return findByProperty(OILELE, oilele, firstResult, maxResults);
	}

	public List findByModify(Object modify, int firstResult, int maxResults) {
		return findByProperty(MODIFY, modify, firstResult, maxResults);
	}

	public List findByExport(Object export, int firstResult, int maxResults) {
		return findByProperty(EXPORT, export, firstResult, maxResults);
	}

	public int getCountByUserid(Object userid) {
		return getCountByProperty(USERID, userid);
	}

	public int getCountByPwd(Object pwd) {
		return getCountByProperty(PWD, pwd);
	}

	public int getCountByName(Object name) {
		return getCountByProperty(NAME, name);
	}

	public int getCountByCellphone(Object cellphone) {
		return getCountByProperty(CELLPHONE, cellphone);
	}

	public int getCountByOilele(Object oilele) {
		return getCountByProperty(OILELE, oilele);
	}

	public int getCountByModify(Object modify) {
		return getCountByProperty(MODIFY, modify);
	}

	public int getCountByExport(Object export) {
		return getCountByProperty(EXPORT, export);
	}

	public List findAll(int firstResult, int maxResults) {
		log.debug("finding all TUser instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TUser.class);
			return getHibernateTemplate().findByCriteria(detachedCriteria,
					firstResult, maxResults);

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public int getCountAll() {
		log.debug("finding all TUser instances");
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TUser.class);

			return ((Integer) getHibernateTemplate().findByCriteria(
					detachedCriteria.setProjection(Projections.rowCount()))
					.get(0)).intValue();

		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TUser merge(TUser detachedInstance) {
		log.debug("merging TUser instance");
		try {
			TUser result = (TUser) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TUser instance) {
		log.debug("attaching dirty TUser instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TUser instance) {
		log.debug("attaching clean TUser instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TUserDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TUserDAO) ctx.getBean("TUserDAO");
	}

	// 查找一个集团下的所有账户
	public List<TUser> findByOrgId(int orgId) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(TUser.class);
			detachedCriteria.add(Restrictions.eq("TOrgainzation.orgId", orgId));
			return getHibernateTemplate().findByCriteria(detachedCriteria);

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}
